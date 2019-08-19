package de.twometer.arduleucht.blocks;

import de.twometer.arduleucht.codegen.CodeWriter;

public abstract class Block {

    public abstract void writeInit(CodeWriter writer);

    public abstract void writeExecute(CodeWriter writer);

}
