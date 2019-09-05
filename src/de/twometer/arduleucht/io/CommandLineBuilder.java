package de.twometer.arduleucht.io;

import java.util.ArrayList;
import java.util.List;

public class CommandLineBuilder {

    private List<String> parts = new ArrayList<>();

    public void append(String raw) {
        parts.add(raw);
    }

    public void addFlag(String flag) {
        parts.add("-" + flag);
    }

    public void addEqualsParameter(String key, String value) {
        parts.add("-" + key + "=" + value);
    }

    public void addParameter(String key, String value) {
        parts.add("-" + key);
        parts.add(value);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (String str : parts) builder.append(str).append(" ");
        return builder.toString().trim();
    }

}
