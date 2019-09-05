package de.twometer.arduleucht.blocks.registry;

import de.twometer.arduleucht.blocks.*;
import de.twometer.arduleucht.blocks.base.Block;
import de.twometer.arduleucht.blocks.model.BlockCategory;

import java.util.ArrayList;
import java.util.List;

public class BlockRegistry {

    private static final Block[] REGISTERED_BLOCKS = new Block[]{
            new ProgramBlock(),
            new ForBlock(),
            new SetPixelBlock(),
            new TetrapixInitBlock(),
            new IntBlock(),
            new StringBlock(),
            new VariableRefBlock(),
            new VariableNameBlock()
    };

    public static List<BlockInfo> getBlocks(BlockCategory category) {
        List<BlockInfo> infos = new ArrayList<>();
        for (Block reg : REGISTERED_BLOCKS)
            if (reg.getCategory() == category)
                infos.add(new BlockInfo(reg.getName(), reg.getClass()));
        return infos;
    }

    public static Block createBlock(String className) {
        try {
            Class clazz = Class.forName(className);
            return (Block) clazz.newInstance();
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
            return null;
        }
    }

}
