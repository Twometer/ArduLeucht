package de.twometer.arduleucht.render;

import de.twometer.arduleucht.blocks.base.Block;
import de.twometer.arduleucht.blocks.model.BlockSocket;
import de.twometer.arduleucht.gui.I18nResolver;

public class BlockSocketShape {

    private static final int MIN_HEIGHT = 25;

    private BlockSocket socket;

    private int height;

    public BlockSocketShape(BlockSocket socket) {
        this.socket = socket;
    }

    void layout(I18nResolver resolver) {
        height = MIN_HEIGHT;
        for (Block block : socket.values()) {
            block.getShape().layout(resolver);
            height += block.getShape().getHeight();
        }
    }

    int getHeight() {
        return height;
    }


}
