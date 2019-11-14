package de.twometer.arduleucht.blocks;

import de.twometer.arduleucht.blocks.base.Block;
import de.twometer.arduleucht.blocks.model.BlockCategory;
import de.twometer.arduleucht.blocks.model.BlockException;
import de.twometer.arduleucht.blocks.model.BlockType;
import de.twometer.arduleucht.build.codegen.CodeEmitter;
import de.twometer.arduleucht.build.codegen.SourceBuilder;

public class IfBlock extends Block {

    public IfBlock() {
        super("blocks.control.if", BlockCategory.CONTROL, BlockType.CONTROL);
        addSocket("condition", BlockType.STATEMENT);
        addSocket("body", BlockType.CONTROL);
    }

    @Override
    public void write(SourceBuilder builder, CodeEmitter scope) throws BlockException {
        scope.openIf(getValue("condition"));
        CodeEmitter child = new CodeEmitter();
        getValue("body").write(builder, child);
        child.pipe(scope);
        scope.closeBlock();
    }
}
