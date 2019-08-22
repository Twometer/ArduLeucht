package de.twometer.arduleucht.gui;

import de.twometer.arduleucht.blocks.base.BlockCategory;
import de.twometer.arduleucht.blocks.registry.BlockInfo;
import de.twometer.arduleucht.blocks.registry.BlockRegistry;
import de.twometer.arduleucht.util.ResourceLoader;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.ResourceBundle;

public class MainController {

    @SuppressWarnings("WeakerAccess") // Let the FXML Loader access this field!
    public ResourceBundle resources;

    public TreeView<String> blocksTreeView;

    public Canvas mainCanvas;

    @FXML
    public void initialize() {
        loadTreeView();
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
        TreeItem<String> root = new TreeItem<>();
        for (BlockCategory category : BlockCategory.values()) {
            String categoryName = i18n(category.getName());
            Image image = new Image(ResourceLoader.getResourceAsStream(category.getIconPath()));

            TreeItem<String> categoryItem = new TreeItem<>(categoryName, new ImageView(image));
            for (BlockInfo info : BlockRegistry.getBlocks(category)) {
                String blockName = i18n(info.getName());
                TreeItem<String> item = new TreeItem<>(blockName, new ImageView(image));
                categoryItem.getChildren().add(item);
            }

            root.getChildren().add(categoryItem);
        }
        blocksTreeView.setRoot(root);
    }

    private String i18n(String key) {
        return resources.getString(key);
    }

}
