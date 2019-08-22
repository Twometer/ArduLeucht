package de.twometer.arduleucht.blocks.base;

import java.util.EnumSet;

public class BlockPort {

    private String portName;

    private EnumSet<BlockType> allowedTypes;

    public BlockPort(String portName, BlockType allowedType) {
        this.portName = portName;
        this.allowedTypes = EnumSet.of(allowedType);
    }

    public BlockPort(String portName, EnumSet<BlockType> allowedTypes) {
        this.portName = portName;
        this.allowedTypes = allowedTypes;
    }

    public String getPortName() {
        return portName;
    }

    public EnumSet<BlockType> getAllowedTypes() {
        return allowedTypes;
    }
}
