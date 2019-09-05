package de.twometer.arduleucht.blocks;

import de.twometer.arduleucht.blocks.base.Block;
import de.twometer.arduleucht.blocks.model.BlockCategory;
import de.twometer.arduleucht.blocks.model.BlockException;
import de.twometer.arduleucht.blocks.model.BlockType;
import de.twometer.arduleucht.codegen.CodeEmitter;
import de.twometer.arduleucht.codegen.SourceBuilder;

public class ForBlock extends Block {
    public ForBlock() {
        super("blocks.control.for_loop", BlockCategory.CONTROL, BlockType.METHOD);
        addSocket("variable", BlockType.DATA).setValue(new VariableNameBlock("i"));
        addSocket("from", BlockType.DATA).setValue(new IntBlock(0));
        addSocket("to", BlockType.DATA).setValue(new IntBlock(5));
        addSocket("step", BlockType.DATA).setValue(new IntBlock(1));
        addSocket("body", BlockType.METHOD);
    }

    @Override
    public void write(SourceBuilder builder, CodeEmitter scope) throws BlockException {
        // TODO scope.openFor();
        CodeEmitter innerScope = new CodeEmitter();
        getValue("body").write(builder, innerScope);
        innerScope.pipe(scope);
        scope.closeBlock();
    }
}
