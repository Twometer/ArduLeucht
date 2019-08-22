package de.twometer.arduleucht.gui;

import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DataFormat;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.util.Callback;

public class DragCellFactory implements Callback<TreeView<String>, TreeCell<String>> {

    static DataFormat DATA_FORMAT = new DataFormat("text/arduleucht-block");

    @Override
    public TreeCell<String> call(TreeView<String> param) {
        TreeCell<String> cell = new TreeCell<String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setText(null);
                    setGraphic(null);
                } else {
                    setGraphic(getTreeItem().getGraphic());
                    setText(item);
                }
            }
        };
        cell.setOnDragDetected(event -> {
            if (cell.getTreeItem() == null) return;
            if (cell.getTreeItem().getParent() == param.getRoot()) return;

            Dragboard db = cell.startDragAndDrop(TransferMode.COPY);

            ClipboardContent content = new ClipboardContent();
            content.put(DATA_FORMAT, cell.getTreeItem().getValue());
            db.setContent(content);
            db.setDragView(cell.snapshot(null, null));
            event.consume();
        });
        return cell;
    }

}
