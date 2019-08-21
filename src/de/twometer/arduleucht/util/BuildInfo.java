package de.twometer.arduleucht.util;

public class BuildInfo {

    private static final String NAME = "ArduLeucht";

    private static final String VERSION = "0.1.0";

    public static String buildTitle() {
        return String.format("%s v%s", NAME, VERSION);
    }

}
