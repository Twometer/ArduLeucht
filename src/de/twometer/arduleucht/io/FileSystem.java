package de.twometer.arduleucht.io;

import java.io.File;

public class FileSystem {

    public static File getTempDir() {
        return new File(System.getProperty("java.io.tmpdir"));
    }

    public static File getInstallDir() {
        return new File("M:\\"); // TODO
    }

}
