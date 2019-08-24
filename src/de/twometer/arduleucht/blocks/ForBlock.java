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
        addSocket("variable", BlockType.DATA);
        addSocket("from", BlockType.DATA);
        addSocket("to", BlockType.DATA);
        addSocket("step", BlockType.DATA);
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
