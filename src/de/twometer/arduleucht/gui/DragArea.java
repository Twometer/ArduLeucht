package de.twometer.arduleucht.gui;

import de.twometer.arduleucht.blocks.base.Block;
import de.twometer.arduleucht.blocks.model.BlockException;

public class DragArea {

    private Block srcBlock;

    private int x;

    private int y;

    private int width;

    private int height;

    private Handler draggedHandler;

    public DragArea(Block srcBlock, int x, int y, int width, int height, Handler draggedHandler) {
        this.srcBlock = srcBlock;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.draggedHandler = draggedHandler;
    }

    Block getSrcBlock() {
        return srcBlock;
    }

    int getX() {
        return x;
    }

    int getY() {
        return y;
    }

    int getWidth() {
        return width;
    }

    int getHeight() {
        return height;
    }

    boolean isOver(int mx, int my) {
        int x = this.x + srcBlock.getShape().getX();
        int y = this.y + srcBlock.getShape().getY();
        return mx >= x && my >= y && mx < x + width && my < y + height;
    }

    Handler getDraggedHandler() {
        return draggedHandler;
    }

    public interface Handler {
        void onDragged(Block draggedBlock) throws BlockException;
    }
}
