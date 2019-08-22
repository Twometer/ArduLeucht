package de.twometer.arduleucht.render;

import de.twometer.arduleucht.blocks.base.Block;
import javafx.scene.canvas.GraphicsContext;

import java.util.UUID;

public class BlockShape {

    private static UUID currentSelectedShape = null;

    private UUID shapeId = UUID.randomUUID();

    private Block block;

    private int x;

    private int y;

    private int width = 100;

    private int height = 100;

    public BlockShape(Block block) {
        this.block = block;
    }

    public void draw(GraphicsContext context) {
        context.setFill(isSelected() ? LeuchtColors.BLUE : LeuchtColors.GREEN);
        context.fillRect(x, y, width, height);
    }

    private boolean isSelected() {
        return shapeId.equals(currentSelectedShape);
    }

    public void select() {
        currentSelectedShape = shapeId;
    }

}
