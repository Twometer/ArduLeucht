package de.twometer.arduleucht.blocks.base;

import de.twometer.arduleucht.blocks.model.BlockCategory;
import de.twometer.arduleucht.blocks.model.BlockException;
import de.twometer.arduleucht.blocks.model.BlockSocket;
import de.twometer.arduleucht.blocks.model.BlockType;
import de.twometer.arduleucht.build.codegen.CodeEmitter;
import de.twometer.arduleucht.build.codegen.SourceBuilder;
import de.twometer.arduleucht.render.BlockShape;

import java.util.ArrayList;
import java.util.List;

public abstract class Block {

    private String name;

    private Block parent;
    private BlockSocket parentSocket;

    private BlockCategory category;

    private BlockType type;

    private BlockShape shape;

    private List<BlockSocket> sockets = new ArrayList<>();

    public Block(String name, BlockCategory category, BlockType type) {
        this.name = name;
        this.category = category;
        this.type = type;
        this.shape = new BlockShape(this);
    }

    public abstract void write(SourceBuilder builder, CodeEmitter scope) throws BlockException;

    public String getName() {
        return name;
    }

    public BlockCategory getCategory() {
        return category;
    }

    public BlockType getType() {
        return type;
    }

    public Block getParent() {
        return parent;
    }

    public BlockSocket getParentSocket() {
        return parentSocket;
    }

    public BlockShape getShape() {
        return shape;
    }

    public void setParent(Block parent, BlockSocket parentSocket) {
        this.parent = parent;
        this.parentSocket = parentSocket;
    }

    public List<BlockSocket> getSockets() {
        return sockets;
    }

    public boolean hasParent() {
        return parent != null && parentSocket != null;
    }

    protected BlockSocket getSocket(String name) throws BlockException {
        for (BlockSocket socket : sockets)
            if (socket.getName().equals(name))
                return socket;
        throw new BlockException("A socket with name " + name + " does not exist on this block");
    }

    protected Block getValue(String name) throws BlockException {
        return getSocket(name).singleValue();
    }

    protected BlockSocket addSocket(String name, BlockType blockType) {
        BlockSocket socket = new BlockSocket(this, name, blockType);
        getSockets().add(socket);
        return socket;
    }

}
