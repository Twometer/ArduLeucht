package de.twometer.arduleucht.gui;

import de.twometer.arduleucht.blocks.model.BlockCategory;
import de.twometer.arduleucht.blocks.registry.BlockInfo;
import de.twometer.arduleucht.blocks.registry.BlockRegistry;
import de.twometer.arduleucht.model.Project;
import de.twometer.arduleucht.util.ResourceLoader;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;

import java.util.ResourceBundle;

public class MainController {

    @SuppressWarnings("WeakerAccess") // Let the FXML Loader access this field!
    public ResourceBundle resources;

    public TreeView<Tagged> blocksTreeView;

    public Canvas mainCanvas;

    public AnchorPane canvasContainer;

    private Project currentProject;

    private boolean dirty = false;

    @FXML
    public void initialize() {
        blocksTreeView.setCellFactory(new DragCellFactory());

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
                System.out.println("Adding block " + tagged.getTag());
            }

            event.setDropCompleted(success);
            event.consume();
        });

        loadTreeView();

        render();
    }

    private void render() {
        GraphicsContext gc = mainCanvas.getGraphicsContext2D();

    }

    @FXML
    public void onNewProject() {

    }

    @FXML
    public void onOpenProject() {

    }

    @FXML
    public void onSaveProject() {

    }

    @FXML
    public void onExit() {

    }

    @FXML
    public void onUpload() {

    }

    @FXML
    public void onAbout() {

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
