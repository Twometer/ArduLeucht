package de.twometer.arduleucht.gui;

import de.twometer.arduleucht.blocks.ProgramBlock;
import de.twometer.arduleucht.blocks.base.Block;
import de.twometer.arduleucht.blocks.base.ConstantBlock;
import de.twometer.arduleucht.blocks.input.ChoiceInputControl;
import de.twometer.arduleucht.blocks.input.InputControl;
import de.twometer.arduleucht.blocks.input.TextInputControl;
import de.twometer.arduleucht.blocks.model.BlockCategory;
import de.twometer.arduleucht.blocks.model.BlockException;
import de.twometer.arduleucht.blocks.registry.BlockInfo;
import de.twometer.arduleucht.blocks.registry.BlockRegistry;
import de.twometer.arduleucht.build.ProjectBuilder;
import de.twometer.arduleucht.build.event.BuildListener;
import de.twometer.arduleucht.build.event.BuildState;
import de.twometer.arduleucht.gui.drag.DragArea;
import de.twometer.arduleucht.gui.drag.DragCellFactory;
import de.twometer.arduleucht.gui.drag.DragController;
import de.twometer.arduleucht.gui.util.I18nResolver;
import de.twometer.arduleucht.gui.util.Tagged;
import de.twometer.arduleucht.gui.util.Theme;
import de.twometer.arduleucht.io.ResourceLoader;
import de.twometer.arduleucht.model.Project;
import de.twometer.arduleucht.render.BlockShape;
import de.twometer.arduleucht.render.api.LeuchtColors;
import de.twometer.arduleucht.render.api.Point;
import de.twometer.arduleucht.util.BuildInfo;
import de.twometer.arduleucht.util.Wrapper;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.TransferMode;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.util.ResourceBundle;
import java.util.UUID;

public class MainController implements I18nResolver {

    @SuppressWarnings("WeakerAccess") // Let the FXML Loader access this field!
    public ResourceBundle resources;

    public TreeView<Tagged> blocksTreeView;

    public Canvas mainCanvas;

    public ScrollPane canvasContainer;

    private Project currentProject;

    private DragController dragController = new DragController();

    private boolean dirty = false;

    @FXML
    public void initialize() {
        blocksTreeView.setCellFactory(new DragCellFactory());

        canvasContainer.setPannable(true);

        mainCanvas.setOnDragOver(event -> {
            if (!event.getDragboard().hasContent(DragCellFactory.DATA_FORMAT))
                return;
            event.acceptTransferModes(TransferMode.COPY);
            event.consume();
        });

        mainCanvas.setOnDragDropped(event -> {
            boolean success = event.getDragboard().hasContent(DragCellFactory.DATA_FORMAT);

            if (success) {
                Tagged tagged = (Tagged) event.getDragboard().getContent(DragCellFactory.DATA_FORMAT);
                Block block = BlockRegistry.createBlock(tagged.getTag());
                if (block == null) return;
                block.getShape().setPosition((int) event.getX(), (int) event.getY());
                currentProject.getTopLevelBlocks().add(block);
                render();
            }

            event.setDropCompleted(success);
            event.consume();
        });

        Wrapper<Point> dragOrigin = new Wrapper<>();
        Wrapper<Point> relativeDragOrigin = new Wrapper<>();
        mainCanvas.setOnMousePressed(event -> {
            if (currentProject == null) return;

            Point clickPoint = new Point(event.getX(), event.getY());
            dragOrigin.set(clickPoint);
            Wrapper<Boolean> hasClicked = new Wrapper<>(false);
            currentProject.iterateAllBlocks(block -> {
                if (block.getShape().getPolygon().test(clickPoint)) {
                    block.getShape().select();
                    relativeDragOrigin.set(new Point(block.getShape().getX() - event.getX(), block.getShape().getY() - event.getY()));
                    hasClicked.set(true);
                    render();
                }
            });

            if (hasClicked.get()) {
                canvasContainer.setPannable(false);
            } else {
                BlockShape.clearSelection();
                render();
            }

            UUID selectedBlock = BlockShape.getSelectedBlock();
            if (selectedBlock != null) {
                Block block = currentProject.findBlock(selectedBlock);
                if (currentProject.getTopLevelBlocks().contains(block)) {
                    currentProject.getTopLevelBlocks().remove(block);
                    currentProject.getTopLevelBlocks().add(block);
                }
                render();
            }
        });

        mainCanvas.setOnMouseReleased(event -> {
            canvasContainer.setPannable(true);
            UUID selectedBlock = BlockShape.getSelectedBlock();
            if (selectedBlock != null) {
                Block block = currentProject.findBlock(selectedBlock);
                block.getShape().setDragging(false);
                render();
            }
        });

        mainCanvas.setOnMouseClicked(event -> {
            if (event.getClickCount() != 2 || currentProject == null) return;
            UUID selectedBlock = BlockShape.getSelectedBlock();
            if (selectedBlock != null) {
                Block block = currentProject.findBlock(selectedBlock);
                if (!(block instanceof ConstantBlock)) return;
                InputControl control = ((ConstantBlock) block).createEditControl();
                String value = inputDialog(control);
                if (value == null || value.trim().length() == 0) return;
                control.getValueConsumer().accept(value);
                render();
            }
        });

        mainCanvas.setOnMouseDragged(event -> {
            UUID selectedBlock = BlockShape.getSelectedBlock();
            if (currentProject != null && selectedBlock != null) {
                Block block = currentProject.findBlock(selectedBlock);
                if (block == null) return;
                block.getShape().setPosition((int) (event.getX() + relativeDragOrigin.get().getX()), (int) (event.getY() + relativeDragOrigin.get().getY()));
                block.getShape().setDragging(true);
                boolean isOverAny = false;
                for (DragArea area : dragController.getDragAreas()) {
                    if (area.isOver((int) event.getX(), (int) event.getY()) && area.getSrcBlock() != block) {
                        try {
                            if (block.hasParent()) block.getParentSocket().removeValue(block);
                            area.getDraggedHandler().onDragged(block);
                            currentProject.removeTopLevelBlock(block);
                            dragOrigin.set(new Point(event.getX(), event.getY()));
                            isOverAny = true;
                        } catch (BlockException e) {
                            // Wrong block type or similar, don't log
                        }
                        area.setHighlighted(true);
                    } else {
                        area.setHighlighted(false);
                    }
                }

                if (!isOverAny && block.hasParent() && dragOrigin.get().distanceTo(event.getX(), event.getY()) > 15) {
                    if (block.hasParent()) block.getParentSocket().removeValue(block);
                    currentProject.getTopLevelBlocks().add(block);
                }

                render();
            }
        });

        Platform.runLater(() -> canvasContainer.getScene().setOnKeyPressed(event -> {
            if (event.getCode() != KeyCode.DELETE || currentProject == null) return;
            UUID selectedBlock = BlockShape.getSelectedBlock();
            if (selectedBlock != null) {
                Block block = currentProject.findBlock(selectedBlock);
                if (block == null) return;
                if (block.hasParent())
                    block.getParentSocket().removeValue(block);
                else currentProject.removeTopLevelBlock(block);
                render();
            }
        }));


        loadTreeView();
        render();
    }

    private void render() {
        if (currentProject == null) return;
        GraphicsContext gc = mainCanvas.getGraphicsContext2D();
        gc.clearRect(0, 0, mainCanvas.getWidth(), mainCanvas.getHeight());

        dragController.reset();
        for (Block block : currentProject.getTopLevelBlocks()) {
            block.getShape().draw(gc, dragController, this);
        }

        gc.setFill(LeuchtColors.PURPLE);
        for (DragArea area : dragController.getDragAreas()) {
            if (area.isHighlighted())
                gc.fillRect(area.getSrcBlock().getShape().getX() + area.getX(), area.getSrcBlock().getShape().getY() + area.getY() + 5, area.getWidth(), 2);
        }
    }

    @FXML
    public void onNewProject() {
        ButtonType action = dirtyConfirmation();
        if (ButtonType.YES.equals(action)) onSaveProject();
        else if (ButtonType.CANCEL.equals(action)) return;
        currentProject = new Project(new File("D:\\test_project"));
        currentProject.create();
        ProgramBlock defaultBlock = new ProgramBlock();
        defaultBlock.getShape().setPosition(50, 50);
        currentProject.getTopLevelBlocks().add(defaultBlock);
        render();
    }

    @FXML
    public void onOpenProject() {
        ButtonType action = dirtyConfirmation();
        if (ButtonType.YES.equals(action)) onSaveProject();
        else if (ButtonType.CANCEL.equals(action)) return;

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open a project");
    }

    @FXML
    public void onSaveProject() {

    }

    @FXML
    public void onExit() {
        ButtonType action = dirtyConfirmation();
        if (ButtonType.YES.equals(action)) onSaveProject();
        else if (ButtonType.CANCEL.equals(action)) return;
        Platform.exit();
    }

    @FXML
    public void onUpload() throws IOException {
        if (currentProject == null)
            return;

        UploadController controller = UploadController.show();
        ProjectBuilder builder = new ProjectBuilder(currentProject);
        builder.setBuildListener(new BuildListener() {
            @Override
            public void onBuildStateChanged(BuildState buildState) {
                switch (buildState) {
                    case COMPILE:
                        controller.setStatus("Compiling...");
                        break;
                    case PLUG_IN:
                        controller.setStatus("Plug in device now!");
                        break;
                    case UPLOADING:
                        controller.setStatus("Uploading...");
                        break;
                }
            }

            @Override
            public void onBuildFailed(String message) {
                controller.close();
                Platform.runLater(() -> {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle(i18n("dialog.fail.title"));
                    alert.setHeaderText(i18n("dialog.fail.header"));
                    alert.setContentText(message);
                    Theme.apply(alert);
                    alert.showAndWait();
                });
            }

            @Override
            public void onBuildSucceeded() {
                controller.close();
                Platform.runLater(() -> {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle(i18n("dialog.success.title"));
                    alert.setContentText(i18n("dialog.success.content"));
                    alert.setHeaderText(null);
                    Theme.apply(alert);
                    alert.showAndWait();
                });
            }
        });
        builder.build();
    }

    @FXML
    public void onAbout() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(i18n("dialog.about.title"));
        alert.setHeaderText(String.format(i18n("dialog.about.header"), BuildInfo.NAME));
        alert.setContentText(String.format(i18n("dialog.about.content"), BuildInfo.VERSION));
        Theme.apply(alert);
        alert.showAndWait();
    }

    private ButtonType dirtyConfirmation() {
        if (!dirty) return null;
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(i18n("dialog.dirty.title"));
        alert.setHeaderText(null);
        alert.setContentText(i18n("dialog.dirty.content"));
        alert.getButtonTypes().setAll(ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
        Theme.apply(alert);
        return alert.showAndWait().orElse(ButtonType.CANCEL);
    }

    private String inputDialog(InputControl control) {
        Dialog<String> dialog = null;
        if (control instanceof TextInputControl)
            dialog = new TextInputDialog();
        else if (control instanceof ChoiceInputControl) {
            String[] choices = ((ChoiceInputControl) control).getChoices().toArray(new String[]{});
            if (choices.length == 0) return "";
            dialog = new ChoiceDialog<>(choices[0], choices);
        }

        if (dialog == null) return "";

        dialog.setTitle(i18n("dialog.input.title"));
        dialog.setHeaderText(i18n(control.getDescriptionKey()));
        Theme.apply(dialog);
        return dialog.showAndWait().orElse("");
    }

    private void loadTreeView() {
        blocksTreeView.setShowRoot(false);

        TreeItem<Tagged> root = new TreeItem<>();

        for (BlockCategory category : BlockCategory.values()) {
            Image image = loadImage(category);
            String categoryName = i18n(category.getName());

            TreeItem<Tagged> categoryItem = new TreeItem<>(new Tagged(categoryName), new ImageView(image));

            for (BlockInfo info : BlockRegistry.getBlocks(category)) {
                String blockName = i18n(info.getName());
                String className = info.getBlockClass().getName();

                Tagged tagged = new Tagged(blockName, className);
                TreeItem<Tagged> item = new TreeItem<>(tagged, new ImageView(image));

                categoryItem.getChildren().add(item);
            }

            root.getChildren().add(categoryItem);
        }
        blocksTreeView.setRoot(root);
    }

    private Image loadImage(BlockCategory category) {
        return new Image(ResourceLoader.getResourceAsStream(category.getIconPath()));
    }

    public String i18n(String key) {
        return resources.getString(key);
    }

}
