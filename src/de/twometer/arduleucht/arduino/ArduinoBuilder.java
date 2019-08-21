package de.twometer.arduleucht.arduino;

import de.twometer.arduleucht.build.BuildException;
import de.twometer.arduleucht.model.Project;
import de.twometer.arduleucht.util.CommandLineBuilder;
import de.twometer.arduleucht.util.FileSystem;

import java.io.File;

public class ArduinoBuilder implements ArduinoSdk {

    private File baseDirectory;

    private File tempDirectory;

    private Project project;

    private File outputFile;

    public ArduinoBuilder(Project project) {
        this.baseDirectory = FileSystem.getInstallDir();
        this.tempDirectory = FileSystem.getTempDir();
        this.project = project;
    }

    @Override
    public String createCommandLine() throws BuildException {
        String buildDir = makeTempDir("arduleucht_build_" + System.currentTimeMillis());
        outputFile = new File(buildDir, "generated.ino.hex");
        return buildCommandLine(project, buildDir);
    }

    public File getOutputFile() {
        return outputFile;
    }

    private String buildCommandLine(Project project, String buildDirectory) {
        CommandLineBuilder builder = new CommandLineBuilder();
        builder.append(getContentFile("arduino-builder"));

        builder.addFlag("compile");
        builder.addEqualsParameter("logger", "machine");

        builder.addParameter("hardware", getContentFile("hardware"));
        builder.addParameter("tools", getContentFile("tools-builder"));
        builder.addParameter("tools", getContentFile("hardware\\tools\\avr"));
        builder.addParameter("built-in-libraries", getContentFile("libraries"));
        builder.addParameter("libraries", getContentFile("libraries"));

        builder.addEqualsParameter("fqbn", "digistump:avr:digispark-tiny");
        builder.addEqualsParameter("ide-version", "10809");
        builder.addParameter("build-path", buildDirectory);

        builder.addEqualsParameter("prefs=build.warn_data_percentage", "75");

        String gccPath = getContentFile("hardware\\arduino\\tools\\avr-gcc\\4.8.1-arduino5");
        builder.addEqualsParameter("prefs=runtime.tools.avr-gcc.path", gccPath);
        builder.addEqualsParameter("prefs=runtime.tools.avr-gcc-4.8.1", gccPath);

        String micronucleusPath = getContentFile("hardware\\digistump\\tools\\micronucleus\\2.0a4");
        builder.addEqualsParameter("prefs=runtime.tools.micronucleus.path", micronucleusPath);
        builder.addEqualsParameter("prefs=runtime.tools.micronucleus-2.0a4.path", micronucleusPath);

        builder.addFlag("verbose");

        builder.append(project.getGeneratedFile().getAbsolutePath());
        return builder.toString();
    }

    private String makeTempDir(String filePath) throws BuildException {
        File file = new File(tempDirectory, filePath);
        if (!file.exists())
            if (!file.mkdirs())
                throw new BuildException("Failed to create temporary build directory");
        return file.getAbsolutePath();
    }

    private String getContentFile(String filePath) {
        return new File(baseDirectory, filePath).getAbsolutePath();
    }
}
