package de.twometer.arduleucht.blocks;

import de.twometer.arduleucht.blocks.base.Block;
import de.twometer.arduleucht.blocks.model.BlockCategory;
import de.twometer.arduleucht.blocks.model.BlockException;
import de.twometer.arduleucht.blocks.model.BlockType;
import de.twometer.arduleucht.codegen.CodeEmitter;
import de.twometer.arduleucht.codegen.SourceBuilder;

public class SetPixelBlock extends Block {

    public SetPixelBlock() {
        super("blocks.tetrapix.set_pixel", BlockCategory.TETRAPIX, BlockType.METHOD);
        addSocket("pin", BlockType.CONSTANT);
        addSocket("red", BlockType.CONSTANT);
        addSocket("green", BlockType.CONSTANT);
        addSocket("blue", BlockType.CONSTANT);
    }

    @Override
    public void write(SourceBuilder builder, CodeEmitter scope) throws BlockException {
        scope.writeCall("strip", "setPixelColor", getValue("red"), getValue("green"), getValue("blue"));
        scope.writeCall("strip", "show");
    }
}
