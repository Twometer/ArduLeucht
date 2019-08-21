package de.twometer.arduleucht.build;

import java.io.IOException;

public interface BuildJob {

    void execute(ProjectBuilder engine) throws IOException, BuildException;

}
