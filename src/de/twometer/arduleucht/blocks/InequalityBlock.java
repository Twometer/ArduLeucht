package de.twometer.arduleucht.blocks;

import de.twometer.arduleucht.blocks.base.Block;
import de.twometer.arduleucht.blocks.model.BlockCategory;
import de.twometer.arduleucht.blocks.model.BlockException;
import de.twometer.arduleucht.blocks.model.BlockType;
import de.twometer.arduleucht.build.codegen.CodeEmitter;
import de.twometer.arduleucht.build.codegen.SourceBuilder;

public class InequalityBlock extends Block {

    public InequalityBlock() {
        super("blocks.data.inequality", BlockCategory.DATA, BlockType.STATEMENT);
        addSocket("left", BlockType.STATEMENT);
        addSocket("right", BlockType.STATEMENT);
    }

    @Override
    public void write(SourceBuilder builder, CodeEmitter scope) throws BlockException {
        getValue("left").write(builder, scope);
        scope.write(" != ");
        getValue("right").write(builder, scope);
    }

}
