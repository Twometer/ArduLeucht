package de.twometer.arduleucht.blocks;

import de.twometer.arduleucht.blocks.base.ConstantBlock;
import de.twometer.arduleucht.blocks.input.InputControl;
import de.twometer.arduleucht.blocks.input.TextInputControl;
import de.twometer.arduleucht.blocks.model.BlockCategory;
import de.twometer.arduleucht.blocks.model.BlockType;
import de.twometer.arduleucht.build.codegen.CodeEmitter;

public class StringBlock extends ConstantBlock<String> {

    public StringBlock() {
        this("");
    }

    private StringBlock(String value) {
        super("blocks.data.string", BlockCategory.DATA, BlockType.DATA, String.class);
        setValue(value);
    }

    @Override
    public InputControl createEditControl() {
        return new TextInputControl(this::setValue)
                .describedBy("dialog.input.string");
    }

    @Override
    public void writeValue(CodeEmitter scope) {
        scope.write(String.format("\"%s\"", getValue()));
    }

}
