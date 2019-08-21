package de.twometer.arduleucht.arduino;

import de.twometer.arduleucht.build.BuildException;

public interface ArduinoSdk {

    String createCommandLine() throws BuildException;

}
