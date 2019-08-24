package de.twometer.arduleucht.blocks;

import de.twometer.arduleucht.blocks.base.ConstantBlock;
import de.twometer.arduleucht.blocks.model.BlockCategory;
import de.twometer.arduleucht.blocks.model.BlockType;
import de.twometer.arduleucht.codegen.CodeEmitter;

public class IntBlock extends ConstantBlock<Integer> {

    public IntBlock() {
        this(0);
    }

    IntBlock(int value) {
        super("blocks.data.integer", BlockCategory.DATA, BlockType.DATA, int.class);
        setValue(value);
    }

    @Override
    public void writeValue(CodeEmitter scope) {
        scope.write(String.valueOf(getValue()));
    }

}
