package de.twometer.arduleucht.blocks;

import de.twometer.arduleucht.blocks.base.Block;
import de.twometer.arduleucht.blocks.base.BlockCategory;
import de.twometer.arduleucht.blocks.base.BlockType;
import de.twometer.arduleucht.codegen.CodeEmitter;
import de.twometer.arduleucht.codegen.SourceBuilder;

public class TetrapixInitBlock extends Block {

    public TetrapixInitBlock() {
        super("blocks.tetrapix.init", BlockCategory.TETRAPIX, BlockType.METHOD);
    }

    @Override
    public void write(SourceBuilder builder, CodeEmitter scope) {
        builder.headScope().writeInclude("Adafruit_NeoPixel.h");
        builder.globalScope().writeStackAlloc("Adafruit_NeoPixel", "strip", "3", "0", "NEO_RGB + NEO_KHZ800");

        scope.writeCall("strip", "begin");
        scope.writeCall("strip", "show");
    }
}
