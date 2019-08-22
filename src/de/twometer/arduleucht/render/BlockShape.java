package de.twometer.arduleucht.render;

import de.twometer.arduleucht.blocks.base.Block;
import javafx.scene.canvas.GraphicsContext;

public class BlockShape {

    private Block block;

    private int x;

    private int y;

    private int width = 100;

    private int height = 100;

    public BlockShape(Block block) {
        this.block = block;
    }

    public void draw(GraphicsContext context) {
        context.setFill(LeuchtColors.GREEN);
        context.fillRect(x, y, width, height);
    }


}
