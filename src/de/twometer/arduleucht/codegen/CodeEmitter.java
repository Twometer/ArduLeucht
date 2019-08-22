package de.twometer.arduleucht.codegen;

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

    public void writeCall(String parent, String method, String... args) {
        writeIndent();
        writef("%s.%s(", parent, method);

        boolean first = true;
        for (String arg : args) {
            if (first) {
                first = false;
                write(arg);
            } else writef(", %s", arg);
        }

        write(");\n");
    }

    public void writeSimpleAlloc(String type, String name, String value) {
        writeIndent();
        writef("%s %s = %s;\n", type, name, value);
    }

    public void writeStackAlloc(String type, String name, String... args) {
        writeIndent();
        writef("%1$s %2$s = %1$s(");

        boolean first = true;
        for (String arg : args) {
            if (first) {
                first = false;
                write(arg);
            } else writef(", %s", arg);
        }

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

    private void write(String str) {
        builder.append(str);
    }

    private void writef(String str, Object... format) {
        builder.append(String.format(str, format));
    }

    private void writeIndent() {
        for (int i = 0; i < indent * 4; i++)
            write(" ");
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

}
