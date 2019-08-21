package de.twometer.arduleucht.build;

import de.twometer.arduleucht.model.Project;

import java.io.IOException;

public class ProjectBuilder {

    private static BuildJob[] JOBS = new BuildJob[]{
            new CodegenJob()
    };

    private Project project;

    public ProjectBuilder(Project project) {
        this.project = project;
    }

    Project getProject() {
        return project;
    }

    public void build(BuildListener buildListener) {
        new Thread(() -> {
            for(BuildJob job : JOBS) {
                try {
                    job.execute(this);
                } catch (IOException | BuildException e) {
                    buildListener.onBuildFailed(e.getMessage());
                    return;
                }
            }
            buildListener.onBuildSucceeded();
        }, "BuildThread").start();
    }

}
