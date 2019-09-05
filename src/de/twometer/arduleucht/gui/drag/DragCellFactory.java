package de.twometer.arduleucht.gui.drag;

import de.twometer.arduleucht.gui.util.Tagged;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DataFormat;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.util.Callback;

public class DragCellFactory implements Callback<TreeView<Tagged>, TreeCell<Tagged>> {

    public static DataFormat DATA_FORMAT = new DataFormat("text/arduleucht-block");

    @Override
    public TreeCell<Tagged> call(TreeView<Tagged> param) {
        TreeCell<Tagged> cell = new TreeCell<Tagged>() {
            @Override
            protected void updateItem(Tagged item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setText(null);
                    setGraphic(null);
                } else {
                    setGraphic(getTreeItem().getGraphic());
                    setText(item.getValue());
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
