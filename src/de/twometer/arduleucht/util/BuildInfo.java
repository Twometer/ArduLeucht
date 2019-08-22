package de.twometer.arduleucht.util;

public class BuildInfo {

    public static final String NAME = "ArduLeucht";

    public static final String VERSION = "0.1.0";

    public static String buildTitle() {
        return String.format("%s v%s", NAME, VERSION);
    }

}
