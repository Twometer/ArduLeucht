package de.twometer.arduleucht.render;

import de.twometer.arduleucht.blocks.base.Block;
import javafx.scene.canvas.GraphicsContext;

public abstract class BlockRenderer {

    private GraphicsContext context;

    public BlockRenderer(GraphicsContext context) {
        this.context = context;
    }

    public GraphicsContext getContext() {
        return context;
    }

    public abstract void layout(Block block);

    public abstract void draw(Block block);

}
