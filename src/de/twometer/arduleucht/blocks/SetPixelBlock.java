package de.twometer.arduleucht.blocks;

import de.twometer.arduleucht.blocks.base.Block;
import de.twometer.arduleucht.blocks.base.BlockCategory;
import de.twometer.arduleucht.blocks.base.BlockType;
import de.twometer.arduleucht.codegen.CodeEmitter;
import de.twometer.arduleucht.codegen.SourceBuilder;

public class SetPixelBlock extends Block {

    public SetPixelBlock() {
        super("blocks.tetrapix.set_pixel", BlockCategory.TETRAPIX, BlockType.METHOD);
    }

    @Override
    public void write(SourceBuilder builder, CodeEmitter scope) {

    }
}
