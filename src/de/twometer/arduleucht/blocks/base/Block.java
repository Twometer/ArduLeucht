package de.twometer.arduleucht.blocks.base;

import de.twometer.arduleucht.blocks.model.BlockCategory;
import de.twometer.arduleucht.blocks.model.BlockException;
import de.twometer.arduleucht.blocks.model.BlockSocket;
import de.twometer.arduleucht.blocks.model.BlockType;
import de.twometer.arduleucht.codegen.CodeEmitter;
import de.twometer.arduleucht.codegen.SourceBuilder;

import java.util.ArrayList;
import java.util.List;

public abstract class Block {

    private String name;

    private Block parent;

    private BlockCategory category;

    private BlockType type;

    private List<BlockSocket> sockets = new ArrayList<>();

    public Block(String name, BlockCategory category, BlockType type) {
        this.name = name;
        this.category = category;
        this.type = type;
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

    public void setParent(Block parent) {
        this.parent = parent;
    }

    private List<BlockSocket> getSockets() {
        return sockets;
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
