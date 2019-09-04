package de.twometer.arduleucht.gui;

import java.util.ArrayList;
import java.util.List;

public class DragController {

    private List<DragArea> dragAreas = new ArrayList<>();

    void reset() {
        dragAreas.clear();
    }

    public void addDragArea(DragArea dragArea) {
        this.dragAreas.add(dragArea);
    }

    List<DragArea> getDragAreas() {
        return dragAreas;
    }
}
