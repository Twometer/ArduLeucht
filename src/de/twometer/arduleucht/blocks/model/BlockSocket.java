package de.twometer.arduleucht.blocks.model;

import de.twometer.arduleucht.blocks.base.Block;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

public class BlockSocket {

    private Block sourceBlock;

    private String name;

    private EnumSet<BlockType> allowedTypes;

    private List<Block> values = new ArrayList<>();

    private boolean allowsMultiple;

    public BlockSocket(Block sourceBlock, String name, BlockType allowedType) {
        this.sourceBlock = sourceBlock;
        this.name = name;
        this.allowedTypes = EnumSet.of(allowedType);
    }

    public BlockSocket(String name, EnumSet<BlockType> allowedTypes) {
        this.name = name;
        this.allowedTypes = allowedTypes;
    }

    public String getName() {
        return name;
    }

    public EnumSet<BlockType> getAllowedTypes() {
        return allowedTypes;
    }

    public void allowMultiple() {
        this.allowsMultiple = true;
    }

    public boolean allowsMultiple() {
        return allowsMultiple;
    }

    public void setValue(Block value) {
        values.clear();
        values.add(value);
        value.setParent(sourceBlock);
    }

    public void addValue(Block value) throws BlockException {
        if (!allowsMultiple)
            throw new BlockException("This socket does not allow multiple values");
        values.add(value);
        value.setParent(sourceBlock);
    }

    public void removeValue(Block value) {
        value.setParent(null);
        values.remove(value);
    }

    public void clearValues() {
        for (Block block : values)
            block.setParent(null);
        values.clear();
    }

    public Block singleValue() throws BlockException {
        if (values.size() != 1)
            throw new BlockException("Expected a single value on socket " + name + ", but got " + values.size());
        return values.get(0);
    }

    public List<Block> values() {
        return values;
    }

}
