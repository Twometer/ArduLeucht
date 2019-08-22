package de.twometer.arduleucht.blocks.base;

import de.twometer.arduleucht.codegen.CodeEmitter;
import de.twometer.arduleucht.codegen.SourceBuilder;

import java.util.ArrayList;
import java.util.List;

public abstract class Block {

    private String name;

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

    protected List<BlockSocket> getSockets() {
        return sockets;
    }

    protected BlockSocket getSocket(String name) throws BlockException {
        for (BlockSocket socket : sockets)
            if (socket.getName().equals(name))
                return socket;
        throw new BlockException("A socket with name " + name + " does not exist on this block");
    }

}
