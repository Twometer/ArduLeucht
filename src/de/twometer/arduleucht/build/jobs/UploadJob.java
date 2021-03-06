package de.twometer.arduleucht.build.jobs;

import de.twometer.arduleucht.build.BuildException;
import de.twometer.arduleucht.build.ProjectBuilder;
import de.twometer.arduleucht.build.arduino.Micronucleus;
import de.twometer.arduleucht.build.event.BuildState;
import de.twometer.arduleucht.io.ConsoleProcess;

import java.io.IOException;

public class UploadJob implements BuildJob {

    private boolean micronucleusOk = false;

    @Override
    public void execute(ProjectBuilder engine) throws BuildException {
        Micronucleus micronucleus = new Micronucleus(engine.getHexFile());
        String uploaderCmd = micronucleus.createCommandLine();
        micronucleusOk = false;

        try {
            ConsoleProcess process = ConsoleProcess.create(uploaderCmd);
            process.setListener(line -> {
                if (line.contains("Plug in device now"))
                    engine.getBuildListener().onBuildStateChanged(BuildState.PLUG_IN);
                else if (line.contains("Device is found"))
                    engine.getBuildListener().onBuildStateChanged(BuildState.UPLOADING);
                else if (line.contains("Device search timed out"))
                    engine.fail("Chip nicht gefunden");
                else if (line.contains("Micronucleus done."))
                    micronucleusOk = true;
            });
            process.waitFor();
        } catch (IOException | InterruptedException e) {
            throw new BuildException(e.getMessage());
        }

        if (!micronucleusOk)
            engine.fail("Den Chip vor dem Hochladen abziehen und erst einstecken, wenn du dazu aufgefordert wirst.");
    }

}
