package de.twometer.arduleucht.render;

import de.twometer.arduleucht.blocks.base.Block;
import de.twometer.arduleucht.blocks.model.BlockSocket;
import de.twometer.arduleucht.gui.I18nResolver;

public class BlockSocketShape {

    private static final int MIN_HEIGHT = 25;

    private BlockSocket socket;

    private int originX;

    private int originY;

    private int height;

    public BlockSocketShape(BlockSocket socket) {
        this.socket = socket;
    }

    void layout(I18nResolver resolver) {
        int height = 0;
        for (Block block : socket.values()) {
            block.getShape().layout(resolver);
            height += block.getShape().getHeight();
        }
        this.height = Math.max(MIN_HEIGHT, height);
    }

    int getHeight() {
        return height;
    }

    void setOrigin(int x, int y) {
        this.originX = x;
        this.originY = y;
    }

    int getOriginX() {
        return originX;
    }

    int getOriginY() {
        return originY;
    }
}
