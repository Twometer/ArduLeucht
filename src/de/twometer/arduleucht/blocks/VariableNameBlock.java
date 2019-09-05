package de.twometer.arduleucht.blocks;

import de.twometer.arduleucht.blocks.base.ConstantBlock;
import de.twometer.arduleucht.blocks.input.InputControl;
import de.twometer.arduleucht.blocks.input.TextInputControl;
import de.twometer.arduleucht.blocks.model.BlockCategory;
import de.twometer.arduleucht.blocks.model.BlockType;
import de.twometer.arduleucht.codegen.CodeEmitter;

public class VariableNameBlock extends ConstantBlock<String> {

    VariableNameBlock(String value) {
        this();
        setValue(value);
    }

    public VariableNameBlock() {
        super("blocks.data.var_name", BlockCategory.DATA, BlockType.DATA, String.class);
    }

    @Override
    public InputControl createEditControl() {
        return new TextInputControl(this::setValue)
                .describedBy("dialog.input.variable_name");
    }

    @Override
    public void writeValue(CodeEmitter scope) {
        scope.write(getValue());
    }

}
