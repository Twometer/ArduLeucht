package de.twometer.arduleucht.model;

import de.twometer.arduleucht.blocks.ProgramBlock;
import de.twometer.arduleucht.blocks.base.Block;
import de.twometer.arduleucht.blocks.model.BlockException;
import de.twometer.arduleucht.blocks.model.BlockSocket;
import de.twometer.arduleucht.util.Wrapper;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;

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

    public Block findBlock(UUID shapeId) {
        Wrapper<Block> block = new Wrapper<>();
        iterateAllBlocks(testBlock -> {
            if (testBlock.getShape().getShapeId() == shapeId)
                block.set(testBlock);
        });
        return block.get();
    }

    public void iterateAllBlocks(Consumer<Block> blockConsumer) {
        for (Block block : topLevelBlocks) {
            blockConsumer.accept(block);
            iterateBlocks(block, blockConsumer);
        }
    }

    private void iterateBlocks(Block parentBlock, Consumer<Block> consumer) {
        for (BlockSocket socket : parentBlock.getSockets()) {
            for (Block block : socket.values()) {
                consumer.accept(block);
                iterateBlocks(block, consumer);
            }
        }
    }

    public List<Block> getTopLevelBlocks() {
        return topLevelBlocks;
    }

    public void removeTopLevelBlock(Block block) {
        topLevelBlocks.remove(block);
    }
}
