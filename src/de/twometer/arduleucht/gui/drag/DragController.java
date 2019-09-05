package de.twometer.arduleucht.gui.drag;

import java.util.ArrayList;
import java.util.List;

public class DragController {

    private List<DragArea> dragAreas = new ArrayList<>();

    public void reset() {
        dragAreas.clear();
    }

    public void addDragArea(DragArea dragArea) {
        this.dragAreas.add(dragArea);
    }

    public List<DragArea> getDragAreas() {
        return dragAreas;
    }
}
