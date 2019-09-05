package de.twometer.arduleucht.build.codegen;

import de.twometer.arduleucht.blocks.model.BlockException;
import de.twometer.arduleucht.build.BuildException;
import de.twometer.arduleucht.model.Project;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;

public class CodeGenerator {

    public void generateCode(Project project) throws FileNotFoundException, BuildException {
        SourceBuilder builder = new SourceBuilder();
        try {
            project.getProgramBlock().write(builder, null);
        } catch (BlockException e) {
            throw new BuildException("Block system error: " + e.getMessage());
        }

        String generatedCode = builder.build();

        File genFile = project.getGeneratedFile();

        if (!tryDelete(genFile))
            throw new BuildException("Could not delete previously generated .ino file");

        PrintWriter writer = new PrintWriter(new FileOutputStream(genFile));
        writer.write(generatedCode);
        writer.close();
    }

    private boolean tryDelete(File genFile) {
        return !genFile.exists() || genFile.delete();
    }

}
