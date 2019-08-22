package de.twometer.arduleucht.blocks.registry;

import de.twometer.arduleucht.blocks.ProgramBlock;
import de.twometer.arduleucht.blocks.base.Block;
import de.twometer.arduleucht.blocks.base.BlockCategory;

import java.util.ArrayList;
import java.util.List;

public class BlockRegistry {

    private static final Block[] REGISTERED_BLOCKS = new Block[]{
            new ProgramBlock()
    };

    public static List<BlockInfo> getBlocks(BlockCategory category) {
        List<BlockInfo> infos = new ArrayList<>();
        for (Block reg : REGISTERED_BLOCKS)
            if (reg.getCategory() == category)
                infos.add(new BlockInfo(reg.getName(), reg.getClass()));
        return infos;
    }

}
