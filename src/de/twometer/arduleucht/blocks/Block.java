package de.twometer.arduleucht.blocks;

import de.twometer.arduleucht.codegen.CodeWriter;

public interface Block {

    void writeIncludes(CodeWriter writer);

    void writeInit(CodeWriter writer);

    void writeExecute(CodeWriter writer);

    BlockType getBlockType();

}
