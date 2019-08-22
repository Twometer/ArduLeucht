package de.twometer.arduleucht.model;

import de.twometer.arduleucht.blocks.ProgramBlock;
import de.twometer.arduleucht.blocks.base.Block;
import de.twometer.arduleucht.blocks.model.BlockException;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Project {

    private File projectFolder;

    private List<Block> topLevelBlocks = new ArrayList<>();

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

    public ProgramBlock getProgramBlock() throws BlockException {
        for (Block block : topLevelBlocks)
            if (block instanceof ProgramBlock)
                return (ProgramBlock) block;
        throw new BlockException("No program block in project");
    }

    public List<Block> getTopLevelBlocks() {
        return topLevelBlocks;
    }
}
