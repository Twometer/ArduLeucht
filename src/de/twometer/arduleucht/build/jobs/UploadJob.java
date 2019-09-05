package de.twometer.arduleucht.build.jobs;

import de.twometer.arduleucht.build.BuildException;
import de.twometer.arduleucht.build.ProjectBuilder;
import de.twometer.arduleucht.build.arduino.Micronucleus;
import de.twometer.arduleucht.build.event.BuildState;
import de.twometer.arduleucht.io.ConsoleProcess;

import java.io.IOException;

public class UploadJob implements BuildJob {

    @Override
    public void execute(ProjectBuilder engine) throws BuildException {
        Micronucleus micronucleus = new Micronucleus(engine.getHexFile());
        String uploaderCmd = micronucleus.createCommandLine();

        try {
            ConsoleProcess process = ConsoleProcess.create(uploaderCmd);
            process.setListener(line -> {
                if (line.contains("Plug in device now"))
                    engine.getBuildListener().onBuildStateChanged(BuildState.PLUG_IN);
                else if (line.contains("Device is found"))
                    engine.getBuildListener().onBuildStateChanged(BuildState.UPLOADING);
            });
            process.waitFor();
        } catch (IOException | InterruptedException e) {
            throw new BuildException(e.getMessage());
        }
    }

}
