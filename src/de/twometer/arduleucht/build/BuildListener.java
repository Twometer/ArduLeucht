package de.twometer.arduleucht.build;

public interface BuildListener {

    void onBuildProgressChanged(int progress);

    void onBuildFailed(String message);

    void onBuildSucceeded();

}
