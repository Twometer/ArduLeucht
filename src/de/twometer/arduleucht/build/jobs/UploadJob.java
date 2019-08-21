package de.twometer.arduleucht.build.jobs;

import de.twometer.arduleucht.arduino.Micronucleus;
import de.twometer.arduleucht.build.ProjectBuilder;

public class UploadJob implements BuildJob {

    @Override
    public void execute(ProjectBuilder engine) {
        Micronucleus micronucleus = new Micronucleus(engine.getHexFile());
        String uploaderCmd = micronucleus.createCommandLine();
    }

}
