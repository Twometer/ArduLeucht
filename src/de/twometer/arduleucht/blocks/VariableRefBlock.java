package de.twometer.arduleucht.blocks;

import de.twometer.arduleucht.blocks.base.ConstantBlock;
import de.twometer.arduleucht.blocks.model.BlockCategory;
import de.twometer.arduleucht.blocks.model.BlockException;
import de.twometer.arduleucht.blocks.model.BlockType;
import de.twometer.arduleucht.codegen.CodeEmitter;

public class VariableRefBlock extends ConstantBlock<String> {

    public VariableRefBlock() {
        super("blocks.data.var_ref", BlockCategory.DATA, BlockType.DATA, String.class);
    }

    @Override
    public void writeValue(CodeEmitter scope) {
        scope.write(getValue());
    }

}
