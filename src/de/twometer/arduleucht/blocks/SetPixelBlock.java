package de.twometer.arduleucht.blocks;

import de.twometer.arduleucht.blocks.base.Block;
import de.twometer.arduleucht.blocks.model.BlockCategory;
import de.twometer.arduleucht.blocks.model.BlockException;
import de.twometer.arduleucht.blocks.model.BlockType;
import de.twometer.arduleucht.build.codegen.CodeEmitter;
import de.twometer.arduleucht.build.codegen.SourceBuilder;

public class SetPixelBlock extends Block {

    public SetPixelBlock() {
        super("blocks.tetrapix.set_pixel", BlockCategory.TETRAPIX, BlockType.CONTROL);
        addSocket("number", BlockType.STATEMENT).setValue(new IntBlock(0));
        addSocket("red", BlockType.STATEMENT).setValue(new IntBlock(255));
        addSocket("green", BlockType.STATEMENT).setValue(new IntBlock(255));
        addSocket("blue", BlockType.STATEMENT).setValue(new IntBlock(255));
    }

    @Override
    public void write(SourceBuilder builder, CodeEmitter scope) throws BlockException {
        scope.writeCall("strip", "setPixelColor", getValue("number"), getValue("red"), getValue("green"), getValue("blue"));
        scope.writeCall("strip", "show");
    }
}
