package de.twometer.arduleucht.blocks;

import de.twometer.arduleucht.blocks.base.Block;
import de.twometer.arduleucht.blocks.base.BlockCategory;
import de.twometer.arduleucht.blocks.base.BlockPort;
import de.twometer.arduleucht.blocks.base.BlockType;
import de.twometer.arduleucht.codegen.CodeWriter;

public class ProgramBlock extends Block {

    public ProgramBlock() {
        super("blocks.program", BlockCategory.CONTROL, BlockType.METHOD);
        getPorts().add(new BlockPort("setup", BlockType.METHOD));
        getPorts().add(new BlockPort("loop", BlockType.METHOD));
    }

    @Override
    public void writeIncludes(CodeWriter writer) {

    }

    @Override
    public void writeInit(CodeWriter writer) {

    }

    @Override
    public void writeExecute(CodeWriter writer) {
        // TODO Write children
        writer.openVoid("setup");
        writer.closeBlock();

        writer.openVoid("loop");
        writer.closeBlock();
    }
}
