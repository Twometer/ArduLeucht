package de.twometer.arduleucht.build.jobs;

import de.twometer.arduleucht.arduino.ArduinoBuilder;
import de.twometer.arduleucht.build.BuildException;
import de.twometer.arduleucht.build.ProjectBuilder;

public class CompileJob implements BuildJob {

    @Override
    public void execute(ProjectBuilder engine) throws BuildException {
        ArduinoBuilder builder = new ArduinoBuilder(engine.getProject());
        String builderCmd = builder.createCommandLine();


        engine.setHexFile(builder.getOutputFile());
    }

}
