package de.twometer.arduleucht.blocks;

import de.twometer.arduleucht.blocks.base.ConstantBlock;
import de.twometer.arduleucht.blocks.input.ChoiceInputControl;
import de.twometer.arduleucht.blocks.input.InputControl;
import de.twometer.arduleucht.blocks.model.BlockCategory;
import de.twometer.arduleucht.blocks.model.BlockType;
import de.twometer.arduleucht.codegen.CodeEmitter;

public class VariableRefBlock extends ConstantBlock<String> {

    public VariableRefBlock() {
        super("blocks.data.var_ref", BlockCategory.DATA, BlockType.DATA, String.class);
    }

    @Override
    public InputControl createEditControl() {
        return new ChoiceInputControl(this::setValue)
                .describedBy("dialog.input.variable");
    }

    @Override
    public void writeValue(CodeEmitter scope) {
        scope.write(getValue());
    }

}
