package de.twometer.arduleucht.build.event;

public interface BuildListener {

    void onBuildProgressChanged(BuildState buildState);

    void onBuildFailed(String message);

    void onBuildSucceeded();

}
