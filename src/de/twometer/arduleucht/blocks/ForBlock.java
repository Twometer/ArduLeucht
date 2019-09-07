package de.twometer.arduleucht.blocks;

import de.twometer.arduleucht.blocks.base.Block;
import de.twometer.arduleucht.blocks.model.BlockCategory;
import de.twometer.arduleucht.blocks.model.BlockException;
import de.twometer.arduleucht.blocks.model.BlockType;
import de.twometer.arduleucht.build.codegen.CodeEmitter;
import de.twometer.arduleucht.build.codegen.SourceBuilder;

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
        scope.openFor(getValue("variable"), getValue("from"), getValue("to"), getValue("step"));
        CodeEmitter innerScope = new CodeEmitter();
        getValue("body").write(builder, innerScope);
        innerScope.pipe(scope);
        scope.closeBlock();
    }
}
