package de.twometer.arduleucht.build;

import de.twometer.arduleucht.build.event.BuildListener;
import de.twometer.arduleucht.build.jobs.BuildJob;
import de.twometer.arduleucht.build.jobs.CompileJob;
import de.twometer.arduleucht.build.jobs.UploadJob;
import de.twometer.arduleucht.model.Project;

import java.io.File;
import java.io.IOException;

public class ProjectBuilder {

    private static BuildJob[] JOBS = new BuildJob[]{
            // new CodegenJob(),
            new CompileJob(),
            new UploadJob()
    };

    private Project project;

    private File hexFile;

    public ProjectBuilder(Project project) {
        this.project = project;
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
