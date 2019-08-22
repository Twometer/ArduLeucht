package de.twometer.arduleucht.gui;

import de.twometer.arduleucht.blocks.base.Block;
import de.twometer.arduleucht.blocks.model.BlockCategory;
import de.twometer.arduleucht.blocks.registry.BlockInfo;
import de.twometer.arduleucht.blocks.registry.BlockRegistry;
import de.twometer.arduleucht.model.Project;
import de.twometer.arduleucht.util.BuildInfo;
import de.twometer.arduleucht.util.ResourceLoader;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.TransferMode;

import java.io.File;
import java.util.ResourceBundle;

public class MainController {

    @SuppressWarnings("WeakerAccess") // Let the FXML Loader access this field!
    public ResourceBundle resources;

    public TreeView<Tagged> blocksTreeView;

    public Canvas mainCanvas;

    public ScrollPane canvasContainer;

    private Project currentProject;

    private boolean dirty = false;

    @FXML
    public void initialize() {
        blocksTreeView.setCellFactory(new DragCellFactory());

        canvasContainer.setPannable(true);
        canvasContainer.setOnDragOver(event -> {
            if (!event.getDragboard().hasContent(DragCellFactory.DATA_FORMAT))
                return;
            event.acceptTransferModes(TransferMode.COPY);
            event.consume();
        });

        canvasContainer.setOnDragDropped(event -> {
            boolean success = event.getDragboard().hasContent(DragCellFactory.DATA_FORMAT);

            if (success) {
                Tagged tagged = (Tagged) event.getDragboard().getContent(DragCellFactory.DATA_FORMAT);
                Block block = BlockRegistry.createBlock(tagged.getTag());
                if (block == null) return;
                currentProject.getTopLevelBlocks().add(block);
                render();
            }

            event.setDropCompleted(success);
            event.consume();
        });

        loadTreeView();

        render();
    }

    private void render() {
        if (currentProject == null) return;
        GraphicsContext gc = mainCanvas.getGraphicsContext2D();
        gc.clearRect(0, 0, mainCanvas.getWidth(), mainCanvas.getHeight());
        for (Block block : currentProject.getTopLevelBlocks())
            block.getShape().draw(gc);
    }

    @FXML
    public void onNewProject() {
        ButtonType action = dirtyConfirmation();
        if (ButtonType.YES.equals(action)) onSaveProject();
        else if (ButtonType.CANCEL.equals(action)) return;
        currentProject = new Project(new File("D:\\test-project"));
    }

    @FXML
    public void onOpenProject() {
        ButtonType action = dirtyConfirmation();
        if (ButtonType.YES.equals(action)) onSaveProject();
        else if (ButtonType.CANCEL.equals(action)) return;

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
    public void onUpload() {

    }

    @FXML
    public void onAbout() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(i18n("dialog.about.title"));
        alert.setHeaderText(String.format(i18n("dialog.about.header"), BuildInfo.NAME));
        alert.setContentText(String.format(i18n("dialog.about.content"), BuildInfo.VERSION));
        alert.showAndWait();
    }

    private ButtonType dirtyConfirmation() {
        if (!dirty) return null;
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(i18n("dialog.dirty.title"));
        alert.setHeaderText(null);
        alert.setContentText(i18n("dialog.dirty.content"));
        alert.getButtonTypes().setAll(ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
        return alert.showAndWait().orElse(ButtonType.CANCEL);
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

    private String i18n(String key) {
        return resources.getString(key);
    }

}
