package de.twometer.arduleucht.gui.drag;

import de.twometer.arduleucht.blocks.base.Block;
import de.twometer.arduleucht.blocks.model.BlockException;

public class DragArea {

    private Block srcBlock;

    private int x;

    private int y;

    private int width;

    private int height;

    private Handler draggedHandler;

    private boolean isHighlighted;

    public DragArea(Block srcBlock, int x, int y, int width, int height, Handler draggedHandler) {
        this.srcBlock = srcBlock;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.draggedHandler = draggedHandler;
    }

    public Block getSrcBlock() {
        return srcBlock;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public boolean isOver(int mx, int my) {
        int x = this.x + srcBlock.getShape().getX();
        int y = this.y + srcBlock.getShape().getY();
        return mx >= x && my >= y && mx < x + width && my < y + height;
    }

    public Handler getDraggedHandler() {
        return draggedHandler;
    }

    public boolean isHighlighted() {
        return isHighlighted;
    }

    public void setHighlighted(boolean highlighted) {
        isHighlighted = highlighted;
    }

    public interface Handler {
        void onDragged(Block draggedBlock) throws BlockException;
    }

}
