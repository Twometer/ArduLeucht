package de.twometer.arduleucht.blocks.registry;

import de.twometer.arduleucht.blocks.base.Block;

public class BlockInfo {

    private String name;

    private Class<? extends Block> blockClass;

    BlockInfo(String name, Class<? extends Block> blockClass) {
        this.name = name;
        this.blockClass = blockClass;
    }

    public String getName() {
        return name;
    }

    public Class<? extends Block> getBlockClass() {
        return blockClass;
    }

    public Block create() {
        try {
            return blockClass.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }
}
