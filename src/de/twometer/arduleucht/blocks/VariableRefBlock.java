package de.twometer.arduleucht.blocks;

import de.twometer.arduleucht.blocks.base.Block;
import de.twometer.arduleucht.blocks.base.ConstantBlock;
import de.twometer.arduleucht.blocks.input.ChoiceInputControl;
import de.twometer.arduleucht.blocks.input.InputControl;
import de.twometer.arduleucht.blocks.model.BlockCategory;
import de.twometer.arduleucht.blocks.model.BlockSocket;
import de.twometer.arduleucht.blocks.model.BlockType;
import de.twometer.arduleucht.build.codegen.CodeEmitter;

public class VariableRefBlock extends ConstantBlock<String> {

    public VariableRefBlock() {
        super("blocks.data.var_ref", BlockCategory.DATA, BlockType.DATA, String.class);
    }

    @Override
    public InputControl createEditControl() {
        ChoiceInputControl inputControl = new ChoiceInputControl(this::setValue);
        inputControl.describedBy("dialog.input.variable");

        Block parent = getParent();
        do {
            for (BlockSocket socket : parent.getSockets())
                for (Block block : socket.values())
                    if (block instanceof VariableNameBlock) {
                        String value = ((VariableNameBlock) block).valueToString();
                        if (value != null)
                            inputControl.getChoices().add(value);
                    }
        } while ((parent = parent.getParent()) != null);

        return inputControl;
    }

    @Override
    public void writeValue(CodeEmitter scope) {
        scope.write(getValue());
    }

}
