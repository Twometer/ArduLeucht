package de.twometer.arduleucht.build.event;

public interface BuildListener {

    void onBuildStateChanged(BuildState buildState);

    void onBuildFailed(String message);

    void onBuildSucceeded();

}
