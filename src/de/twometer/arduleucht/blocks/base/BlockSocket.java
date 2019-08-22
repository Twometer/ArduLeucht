package de.twometer.arduleucht.blocks.base;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

public class BlockSocket {

    private String name;

    private EnumSet<BlockType> allowedTypes;

    private List<Block> values = new ArrayList<>();

    private boolean allowsMultiple;

    public BlockSocket(String name, BlockType allowedType) {
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

    public BlockSocket allowMultiple() {
        this.allowsMultiple = true;
        return this;
    }

    public boolean allowsMultiple() {
        return allowsMultiple;
    }

    public void setValue(Block value) {
        values.clear();
        values.add(value);
    }

    public void addValue(Block value) throws BlockException {
        if (!allowsMultiple)
            throw new BlockException("This socket does not allow multiple values");
        values.add(value);
    }

    public void clearValues() {
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
