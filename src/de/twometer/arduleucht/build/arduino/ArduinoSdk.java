package de.twometer.arduleucht.build.arduino;

import de.twometer.arduleucht.build.BuildException;

public interface ArduinoSdk {

    String createCommandLine() throws BuildException;

}
