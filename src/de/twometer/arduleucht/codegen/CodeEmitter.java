package de.twometer.arduleucht.codegen;

import de.twometer.arduleucht.blocks.base.Block;
import de.twometer.arduleucht.blocks.model.BlockException;

import java.util.ArrayList;
import java.util.List;

public class CodeEmitter {

    private StringBuilder builder = new StringBuilder();

    private List<String> includes = new ArrayList<>();

    private int indent;

    public void writeInclude(String file) {
        if (includes.contains(file)) return;
        includes.add(file);
        writef("#include <%s>\n", file);
    }

    public void writeCall(String parent, String method, Object... args) throws BlockException {
        writeIndent();
        writef("%s.%s(", parent, method);
        writeArgList(args);
        write(");\n");
    }

    public void writeSimpleAlloc(String type, String name, String value) {
        writeIndent();
        writef("%s %s = %s;\n", type, name, value);
    }

    public void writeStackAlloc(String type, String name, Object... args) throws BlockException {
        writeIndent();
        writef("%1$s %2$s = %1$s(");
        writeArgList(args);
        write(");\n");
    }

    void openVoid(String name) {
        writeIndent();
        writef("void %s() {\n", name);
        indent++;
    }

    public void openFor(String variable, String from, String to, String step) {
        writeIndent();
        writef("for (%1$s = %2$s; %1$s < %3$s; %1$s++) {\n", variable, from, to, step);
        indent++;
    }

    void closeBlock() {
        indent--;
        writeIndent();

        write("}\n");
    }

    String getCode() {
        return builder.toString();
    }

    void pipe(CodeEmitter out) {
        for (String line : getCode().split("\n")) {
            out.writeIndent();
            out.writef("%s\n", line);
        }
    }

    public void write(String str) {
        builder.append(str);
    }

    private void writef(String str, Object... format) {
        builder.append(String.format(str, format));
    }

    private void writeIndent() {
        for (int i = 0; i < indent * 4; i++)
            write(" ");
    }

    private void writeObject(Object object) throws BlockException {
        if (object instanceof String)
            write((String) object);
        else if (object instanceof Block)
            ((Block) object).write(null, this);
        else throw new BlockException("Unsupported object " + object.getClass().getName());
    }

    private void writeArgList(Object... args) throws BlockException {
        boolean first = true;
        for (Object arg : args) {
            if (first) {
                first = false;
                writeObject(arg);
            } else {
                write(", ");
                writeObject(arg);
            }
        }

    }

}
