package de.twometer.arduleucht.blocks;

import de.twometer.arduleucht.blocks.base.ConstantBlock;
import de.twometer.arduleucht.blocks.model.BlockCategory;
import de.twometer.arduleucht.blocks.model.BlockType;
import de.twometer.arduleucht.codegen.CodeEmitter;

public class IntBlock extends ConstantBlock<Integer> {

    public IntBlock() {
        super("blocks.variable.integer", BlockCategory.VARIABLES, BlockType.CONSTANT, int.class);
    }

    @Override
    public void writeValue(CodeEmitter scope) {
        scope.write(String.valueOf(getValue()));
    }

}
