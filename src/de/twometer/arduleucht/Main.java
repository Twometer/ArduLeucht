package de.twometer.arduleucht;

import de.twometer.arduleucht.arduino.ArduinoBuilder;
import de.twometer.arduleucht.arduino.Micronucleus;
import de.twometer.arduleucht.build.BuildException;
import de.twometer.arduleucht.model.Project;

import java.io.File;

public class Main {

    public static void main(String[] args) throws BuildException {
        Project project = new Project(new File("D:\\test_project"));
        ArduinoBuilder builder = new ArduinoBuilder(project);
        String builderCmd = builder.createCommandLine();

        Micronucleus micronucleus = new Micronucleus(builder.getOutputFile());
        String uploaderCmd = micronucleus.createCommandLine();

        System.out.println(builderCmd);
        System.out.println(uploaderCmd);
    }

}
