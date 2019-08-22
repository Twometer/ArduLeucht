package de.twometer.arduleucht.blocks;

import de.twometer.arduleucht.blocks.base.Block;
import de.twometer.arduleucht.blocks.model.BlockCategory;
import de.twometer.arduleucht.blocks.model.BlockException;
import de.twometer.arduleucht.blocks.model.BlockType;
import de.twometer.arduleucht.codegen.CodeEmitter;
import de.twometer.arduleucht.codegen.SourceBuilder;

public class ProgramBlock extends Block {

    public ProgramBlock() {
        super("blocks.program", BlockCategory.CONTROL, BlockType.METHOD);
        addSocket("setup", BlockType.METHOD).allowMultiple();
        addSocket("loop", BlockType.METHOD).allowMultiple();
    }

    @Override
    public void write(SourceBuilder builder, CodeEmitter scope) throws BlockException {
        CodeEmitter setupScope = builder.privateScope("setup");
        CodeEmitter loopScope = builder.privateScope("loop");

        for (Block val : getSocket("setup").values())
            val.write(builder, setupScope);

        for (Block val : getSocket("loop").values())
            val.write(builder, loopScope);
    }

}
