package de.twometer.arduleucht.render;

import de.twometer.arduleucht.blocks.base.Block;
import de.twometer.arduleucht.blocks.base.ConstantBlock;
import de.twometer.arduleucht.blocks.model.BlockSocket;
import de.twometer.arduleucht.blocks.model.BlockType;
import de.twometer.arduleucht.gui.I18nResolver;
import de.twometer.arduleucht.render.api.Polygon;
import de.twometer.arduleucht.render.api.TextMetrics;
import javafx.geometry.Bounds;
import javafx.geometry.VPos;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;

import java.util.UUID;

public class BlockShape {

    private static final int TEXT_PADDING = 5;
    private static final int SOCKET_WIDTH = 15;
    private static final int SOCKET_PADDING = 25;

    private Block block;

    private static UUID selectedId = null;

    private UUID shapeId = UUID.randomUUID();

    private Polygon polygon;

    private int x;

    private int y;

    private int width;

    private int height;

    public BlockShape(Block block) {
        this.block = block;
    }

    void layout(I18nResolver resolver) {
        width = (int) (TextMetrics.getInstance().measure(resolver.i18n(block.getName())).getWidth()) + TEXT_PADDING * 2;
        height = 10;

        if (block instanceof ConstantBlock) {
            Bounds textBounds = TextMetrics.getInstance().measure("value_here");
            this.height = (int) (textBounds.getHeight() + TEXT_PADDING * 2);
            return;
        }

        for (BlockSocket socket : block.getSockets()) {
            socket.getShape().layout(resolver);

            height += socket.getShape().getHeight() + SOCKET_PADDING;

            double curWidth = TextMetrics.getInstance().measure(socket.getName()).getWidth() + SOCKET_WIDTH + TEXT_PADDING * 2;
            if (curWidth > width)
                width = (int) curWidth;
        }
    }

    public void draw(GraphicsContext context, I18nResolver resolver) {
        layout(resolver);

        DropShadow shadow = new DropShadow();
        shadow.setColor(Color.rgb(0, 0, 0, .5));
        context.setEffect(shadow);

        switch (block.getType()) {
            case METHOD:
                context.setFill(LeuchtColors.ORANGE);
                break;
            case DATA:
                context.setFill(LeuchtColors.BLUE);
                break;
            case OPERATION:
                context.setFill(LeuchtColors.GREEN);
                break;
        }

        if (block instanceof ConstantBlock)
            drawConstant(context, resolver);
        else
            drawRegular(context, resolver);

        for (BlockSocket socket : block.getSockets()) {
            int xOffset = socket.getShape().getOriginX();
            int yOffset = socket.getShape().getOriginY();
            for (Block block : socket.values()) {
                block.getShape().x = xOffset;
                block.getShape().y = yOffset;
                block.getShape().draw(context, resolver);
                yOffset += block.getShape().getHeight();
            }
        }
    }

    private void drawConstant(GraphicsContext context, I18nResolver resolver) {
        polygon = new Polygon(this.x, this.y);
        polygon.addPoint(0, this.height / 2d);
        polygon.addPoint(SOCKET_WIDTH, 0);
        polygon.addPoint(SOCKET_WIDTH + this.width, 0);
        polygon.addPoint(2 * SOCKET_WIDTH + this.width, this.height / 2d);
        polygon.addPoint(SOCKET_WIDTH + this.width, this.height);
        polygon.addPoint(SOCKET_WIDTH, this.height);
        context.setLineWidth(isSelected() ? 2 : 1);
        polygon.render(context, isSelected() ? LeuchtColors.SILVER : LeuchtColors.ASPHALT);
    }

    private void drawRegular(GraphicsContext context, I18nResolver resolver) {
        polygon = new Polygon(this.x, this.y);
        polygon.addPoint(0, 0);
        polygon.addPoint(width, 0);

        int yo = SOCKET_PADDING + 5;
        polygon.addPoint(width, yo);
        for (BlockSocket socket : block.getSockets()) {

            if (socket.getAllowedTypes().contains(BlockType.METHOD)) {
                polygon.addPoint(width - SOCKET_WIDTH, yo);
                socket.getShape().setOrigin(this.x + width - SOCKET_WIDTH, this.y + yo);
                yo += socket.getShape().getHeight();
                polygon.addPoint(width - SOCKET_WIDTH, yo);
                polygon.addPoint(width, yo);
            } else {
                int socketHeight = socket.getShape().getHeight();
                polygon.addPoint(width - SOCKET_WIDTH, yo + (socketHeight / 2d));
                socket.getShape().setOrigin(this.x + width - SOCKET_WIDTH, this.y + yo);
                yo += socketHeight;
                polygon.addPoint(width, yo);
            }

            yo += SOCKET_PADDING;
            if (socket != block.getSockets().get(block.getSockets().size() - 1)) polygon.addPoint(width, yo);
        }

        polygon.addPoint(width, yo - (SOCKET_PADDING - 5));
        polygon.addPoint(0, yo - (SOCKET_PADDING - 5));
        context.setLineWidth(isSelected() ? 2 : 1);
        polygon.render(context, isSelected() ? LeuchtColors.SILVER : LeuchtColors.ASPHALT);

        context.setFill(Color.WHITE);
        context.setTextBaseline(VPos.TOP);

        context.fillText(resolver.i18n(block.getName()), x + TEXT_PADDING, y + TEXT_PADDING);

        context.setTextBaseline(VPos.CENTER);
        yo = SOCKET_PADDING;
        for (BlockSocket socket : block.getSockets()) {
            Bounds textSize = TextMetrics.getInstance().measure(socket.getName());
            context.fillText(socket.getName(), x + width - SOCKET_WIDTH - TEXT_PADDING - textSize.getWidth(), this.y + yo + socket.getShape().getHeight() / 2d);
            yo += socket.getShape().getHeight() + SOCKET_PADDING;
        }
    }

    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    int getHeight() {
        return height;
    }

    private boolean isSelected() {
        return selectedId == this.shapeId;
    }

    public UUID getShapeId() {
        return shapeId;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void select() {
        selectedId = this.shapeId;
    }

    public static UUID getSelectedBlock() {
        return selectedId;
    }

    public static void clearSelection() {
        selectedId = null;
    }

    public Polygon getPolygon() {
        return polygon;
    }
}
