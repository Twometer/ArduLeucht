package de.twometer.arduleucht.build.jobs;

import de.twometer.arduleucht.build.BuildException;
import de.twometer.arduleucht.build.ProjectBuilder;

import java.io.IOException;

public interface BuildJob {

    void execute(ProjectBuilder engine) throws IOException, BuildException;

}
