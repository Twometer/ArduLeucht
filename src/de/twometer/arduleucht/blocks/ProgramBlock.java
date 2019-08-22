package de.twometer.arduleucht.blocks;

import de.twometer.arduleucht.blocks.base.*;
import de.twometer.arduleucht.codegen.CodeEmitter;
import de.twometer.arduleucht.codegen.SourceBuilder;

public class ProgramBlock extends Block {

    public ProgramBlock() {
        super("blocks.program", BlockCategory.CONTROL, BlockType.METHOD);
        getSockets().add(new BlockSocket("setup", BlockType.METHOD).allowMultiple());
        getSockets().add(new BlockSocket("loop", BlockType.METHOD).allowMultiple());
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
