package de.twometer.arduleucht.model;

import de.twometer.arduleucht.blocks.ProgramBlock;

import java.io.File;

public class Project {

    private File projectFolder;

    private ProgramBlock programBlock = new ProgramBlock();

    public Project(File projectFolder) {
        this.projectFolder = projectFolder;
    }

    public File getProjectFolder() {
        return projectFolder;
    }

    public File getGeneratedFile() {
        return new File(projectFolder, "generated.ino");
    }

    public void setProjectFolder(File projectFolder) {
        this.projectFolder = projectFolder;
    }

    public ProgramBlock getProgramBlock() {
        return programBlock;
    }

}
