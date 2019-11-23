package de.twometer.arduleucht.build.arduino;

import de.twometer.arduleucht.io.CommandLineBuilder;
import de.twometer.arduleucht.io.FileSystem;

import java.io.File;

public class Micronucleus implements ArduinoSdk {

    private File baseDirectory;

    private File hexFile;

    public Micronucleus(File hexFile) {
        this.baseDirectory = FileSystem.getInstallDir();
        this.hexFile = hexFile;
    }

    @Override
    public String createCommandLine() {
        CommandLineBuilder builder = new CommandLineBuilder();
        builder.append("\"" + getContentFile("hardware\\digistump\\tools\\micronucleus\\2.0a4\\launcher") + "\"");
        builder.addFlag("cdigispark");
        builder.addParameter("-timeout", "60");
        builder.append(String.format("-Uflash:w:%s:i ", hexFile.getAbsolutePath()));
        return builder.toString();
    }

    private String getContentFile(String filePath) {
        return new File(baseDirectory, filePath).getAbsolutePath();
    }

}
