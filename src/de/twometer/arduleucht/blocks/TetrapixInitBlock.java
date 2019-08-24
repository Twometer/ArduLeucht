package de.twometer.arduleucht.blocks;

import de.twometer.arduleucht.blocks.base.Block;
import de.twometer.arduleucht.blocks.model.BlockCategory;
import de.twometer.arduleucht.blocks.model.BlockException;
import de.twometer.arduleucht.blocks.model.BlockType;
import de.twometer.arduleucht.codegen.CodeEmitter;
import de.twometer.arduleucht.codegen.SourceBuilder;

public class TetrapixInitBlock extends Block {

    public TetrapixInitBlock() {
        super("blocks.tetrapix.init", BlockCategory.TETRAPIX, BlockType.METHOD);
        addSocket("led_count", BlockType.DATA);
    }

    @Override
    public void write(SourceBuilder builder, CodeEmitter scope) throws BlockException {
        builder.headScope().writeInclude("Adafruit_NeoPixel.h");

        Block ledCont = getSocket("led_count").singleValue();
        builder.globalScope().writeStackAlloc("Adafruit_NeoPixel", "strip", ledCont, "0", "NEO_RGB + NEO_KHZ800");

        scope.writeCall("strip", "begin");
        scope.writeCall("strip", "show");
    }
}
