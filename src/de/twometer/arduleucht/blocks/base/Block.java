package de.twometer.arduleucht.blocks.base;

import de.twometer.arduleucht.codegen.CodeWriter;

import java.util.ArrayList;
import java.util.List;

public abstract class Block {

    private String name;

    private BlockCategory category;

    private BlockType type;

    private List<BlockPort> ports = new ArrayList<>();

    public Block(String name, BlockCategory category, BlockType type) {
        this.name = name;
        this.category = category;
        this.type = type;
    }

    public abstract void writeIncludes(CodeWriter writer);

    public abstract void writeInit(CodeWriter writer);

    public abstract void writeExecute(CodeWriter writer);

    public String getName() {
        return name;
    }

    public BlockCategory getCategory() {
        return category;
    }

    public BlockType getType() {
        return type;
    }

    protected List<BlockPort> getPorts() {
        return ports;
    }
}
