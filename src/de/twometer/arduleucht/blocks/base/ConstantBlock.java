package de.twometer.arduleucht.blocks.base;

import de.twometer.arduleucht.blocks.input.InputControl;
import de.twometer.arduleucht.blocks.model.BlockCategory;
import de.twometer.arduleucht.blocks.model.BlockException;
import de.twometer.arduleucht.blocks.model.BlockType;
import de.twometer.arduleucht.codegen.CodeEmitter;
import de.twometer.arduleucht.codegen.SourceBuilder;

/**
 * A constant block is a block the user can define the raw content of.
 * It is used to e.g. specify constant integers etc.
 * As this block is used only to store a single value, it's write method
 * does not have access to the SourceBuilder. It may only write at the current
 * position of the current scope.
 *
 * @param <T> The constant's data type
 */
public abstract class ConstantBlock<T> extends Block {

    private Class<T> inputType;

    private T value;

    public ConstantBlock(String name, BlockCategory category, BlockType type, Class<T> inputType) {
        super(name, category, type);
        this.inputType = inputType;
    }

    @Override
    public final void write(SourceBuilder builder, CodeEmitter scope) throws BlockException {
        writeValue(scope);
    }

    public abstract InputControl createEditControl();

    public abstract void writeValue(CodeEmitter scope) throws BlockException;

    protected T getValue() {
        return value;
    }

    protected void setValue(T value) {
        this.value = value;
    }

    public String valueToString() {
        return String.valueOf(value);
    }

    public Class<T> getInputType() {
        return inputType;
    }
}
