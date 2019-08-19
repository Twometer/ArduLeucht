package de.twometer.tetrapix.blocks;

import de.twometer.tetrapix.codegen.CodeWriter;

public abstract class Block {

    public abstract void writeInit(CodeWriter writer);

    public abstract void writeExecute(CodeWriter writer);

}
