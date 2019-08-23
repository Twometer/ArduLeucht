package de.twometer.arduleucht.render;

import de.twometer.arduleucht.blocks.base.Block;
import de.twometer.arduleucht.blocks.model.BlockSocket;
import de.twometer.arduleucht.blocks.model.BlockType;
import de.twometer.arduleucht.gui.I18nResolver;
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

    private static UUID currentSelectedShape = null;

    private UUID shapeId = UUID.randomUUID();

    private Block block;

    private int x = 10;

    private int y = 10;

    private int width = 100;

    private int height;

    public BlockShape(Block block) {
        this.block = block;
    }

    void layout(I18nResolver resolver) {

        width = (int) (TextMetrics.getInstance().measure(resolver.i18n(block.getName())).getWidth()) + TEXT_PADDING * 2;
        for (BlockSocket socket : block.getSockets()) {
            socket.getShape().layout(resolver);

            double curWidth = TextMetrics.getInstance().measure(socket.getName()).getWidth() + SOCKET_WIDTH + TEXT_PADDING * 2;
            if (curWidth > width)
                width = (int) curWidth;
        }
    }

    public void draw(GraphicsContext context, I18nResolver resolver) {
        layout(resolver);

        context.setEffect(new DropShadow());

        switch (block.getType()) {
            case METHOD:
                context.setFill(LeuchtColors.ORANGE);
                break;
            case VARIABLE:
                context.setFill(LeuchtColors.PURPLE);
                break;
            case CONSTANT:
                context.setFill(LeuchtColors.BLUE);
            case OPERATION:
                context.setFill(LeuchtColors.GREEN);
                break;
        }

        Polygon polygon = new Polygon(this.x, this.y);
        polygon.addPoint(0, 0);
        polygon.addPoint(width, 0);

        int yo = SOCKET_PADDING + 5;
        polygon.addPoint(width, yo);
        for (BlockSocket socket : block.getSockets()) {

            if (socket.getAllowedTypes().contains(BlockType.METHOD)) {
                polygon.addPoint(width - SOCKET_WIDTH, yo);
                yo += socket.getShape().getHeight();
                polygon.addPoint(width - SOCKET_WIDTH, yo);
                polygon.addPoint(width, yo);
            } else {
                int socketHeight = socket.getShape().getHeight();
                polygon.addPoint(width - SOCKET_WIDTH, yo + (socketHeight / 2d));
                yo += socketHeight;
                polygon.addPoint(width, yo);
            }

            yo += SOCKET_PADDING;
            if (socket != block.getSockets().get(block.getSockets().size() - 1)) polygon.addPoint(width, yo);
        }

        height = yo;

        polygon.addPoint(width, yo - (SOCKET_PADDING - 5));
        polygon.addPoint(0, yo - (SOCKET_PADDING - 5));
        polygon.render(context);

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

    private boolean isSelected() {
        return shapeId.equals(currentSelectedShape);
    }

    public void select() {
        currentSelectedShape = shapeId;
    }

    int getHeight() {
        return height;
    }
}
