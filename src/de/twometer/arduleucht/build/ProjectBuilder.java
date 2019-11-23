package de.twometer.arduleucht.build;

import de.twometer.arduleucht.build.event.BuildListener;
import de.twometer.arduleucht.build.event.BuildState;
import de.twometer.arduleucht.build.jobs.BuildJob;
import de.twometer.arduleucht.build.jobs.CodegenJob;
import de.twometer.arduleucht.build.jobs.CompileJob;
import de.twometer.arduleucht.build.jobs.UploadJob;
import de.twometer.arduleucht.model.Project;
import de.twometer.arduleucht.util.Log;

import java.io.File;
import java.io.IOException;

public class ProjectBuilder {

    private static BuildJob[] JOBS = new BuildJob[]{
            new CodegenJob(),
            new CompileJob(),
            new UploadJob()
    };

    private Project project;

    private File hexFile;

    private volatile boolean hasFailed = false;

    private BuildListener buildListener = new BuildListener() {
        @Override
        public void onBuildStateChanged(BuildState buildState) {

        }

        @Override
        public void onBuildFailed(String message) {

        }

        @Override
        public void onBuildSucceeded() {

        }
    };

    public ProjectBuilder(Project project) {
        this.project = project;
    }

    public void build() {
        new Thread(() -> {
            for (BuildJob job : JOBS) {
                try {
                    job.execute(this);
                    if (hasFailed)
                        return;
                } catch (IOException | BuildException e) {
                    buildListener.onBuildFailed(e.getMessage());
                    return;
                }
            }
            buildListener.onBuildSucceeded();
        }, "BuildThread").start();
    }

    public void fail(String message) {
        Log.i("Build has failed: " + message);
        hasFailed = true;
        buildListener.onBuildFailed(message);
    }

    public BuildListener getBuildListener() {
        return buildListener;
    }

    public void setBuildListener(BuildListener buildListener) {
        this.buildListener = buildListener;
    }

    public Project getProject() {
        return project;
    }

    public File getHexFile() {
        return hexFile;
    }

    public void setHexFile(File hexFile) {
        this.hexFile = hexFile;
    }
}
