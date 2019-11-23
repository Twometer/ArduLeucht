package de.twometer.arduleucht.util;

public class Log {

    public static void i(String message) {
        o("INFO", message);
    }

    public static void w(String message) {
        o("WARN", message);
    }

    public static void e(String message) {
        o("ERROR", message);
    }

    private static void o(String tag, String message) {
        System.out.printf("[%s] %s%n", tag, message);
    }

}
