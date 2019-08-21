package de.twometer.arduleucht;

import de.twometer.arduleucht.build.ProjectBuilder;
import de.twometer.arduleucht.build.event.BuildListener;
import de.twometer.arduleucht.build.event.BuildState;
import de.twometer.arduleucht.model.Project;

import java.io.File;

public class Main {

    public static void main(String[] args) {
        Project project = new Project(new File("D:\\test_project"));
        ProjectBuilder builder = new ProjectBuilder(project);

        builder.setBuildListener(new BuildListener() {
            @Override
            public void onBuildStateChanged(BuildState buildState) {
                System.out.println("Build state: " + buildState);
            }

            @Override
            public void onBuildFailed(String message) {
                System.out.println("Build failed: " + message);
            }

            @Override
            public void onBuildSucceeded() {
                System.out.println("Build successful");
            }
        });

        builder.build();
    }

}
