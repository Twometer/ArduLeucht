package de.twometer.arduleucht.build.jobs;

import de.twometer.arduleucht.arduino.ArduinoBuilder;
import de.twometer.arduleucht.build.BuildException;
import de.twometer.arduleucht.build.ProjectBuilder;
import de.twometer.arduleucht.build.event.BuildState;
import de.twometer.arduleucht.util.ConsoleProcess;

import java.io.IOException;

public class CompileJob implements BuildJob {

    @Override
    public void execute(ProjectBuilder engine) throws BuildException {
        ArduinoBuilder builder = new ArduinoBuilder(engine.getProject());
        String builderCmd = builder.createCommandLine();

        engine.getBuildListener().onBuildStateChanged(BuildState.COMPILE);

        try {
            ConsoleProcess process = ConsoleProcess.create(builderCmd);
            process.waitFor();
        } catch (IOException | InterruptedException e) {
            throw new BuildException(e.getMessage());
        }

        engine.setHexFile(builder.getOutputFile());
    }

}