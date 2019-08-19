package de.twometer.tetrapix.codegen;

public class CodeWriter {

    private StringBuilder builder = new StringBuilder();

    public void writeInclude(String file) {
        writef("#include <%s>\n", file);
    }

    public void writeStackAlloc(String type, String name, String... args) {
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

    public void openVoid(String name) {
        writef("void %s() {", name);
    }

    public void closeBlock() {
        write("}\n");
    }

    private void write(String str) {
        builder.append(str);
    }

    private void writef(String str, Object... format) {
        builder.append(String.format(str, format));
    }

    public String getCode() {
        return builder.toString();
    }

}
