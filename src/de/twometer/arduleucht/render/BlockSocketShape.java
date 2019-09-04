package de.twometer.arduleucht.render;

import de.twometer.arduleucht.blocks.base.Block;
import de.twometer.arduleucht.blocks.model.BlockSocket;
import de.twometer.arduleucht.gui.DragController;
import de.twometer.arduleucht.gui.I18nResolver;
import de.twometer.arduleucht.render.api.Point;

public class BlockSocketShape {

    private static final int MIN_HEIGHT = 25;

    private BlockSocket socket;

    private Point origin;

    private int height;

    public BlockSocketShape(BlockSocket socket) {
        this.socket = socket;
    }

    void layout(DragController controller, I18nResolver resolver) {
        int height = 0;
        for (Block block : socket.values()) {
            block.getShape().layout(controller, resolver);
            height += block.getShape().getHeight();
        }
        this.height = Math.max(MIN_HEIGHT, height);
    }

    int getHeight() {
        return height;
    }

    void setOrigin(int x, int y) {
        this.origin = new Point(x, y);
    }

    int getOriginX() {
        return (int) origin.getX();
    }

    int getOriginY() {
        return (int) origin.getY();
    }
}
