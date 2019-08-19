package de.twometer.arduleucht.model;

import de.twometer.arduleucht.blocks.Block;

import java.io.File;

public class Project {

    private File projectFolder;

    private Block mainBlock;

    public File getProjectFolder() {
        return projectFolder;
    }

    public void setProjectFolder(File projectFolder) {
        this.projectFolder = projectFolder;
    }

    public Block getMainBlock() {
        return mainBlock;
    }

    public void setMainBlock(Block mainBlock) {
        this.mainBlock = mainBlock;
    }
}
