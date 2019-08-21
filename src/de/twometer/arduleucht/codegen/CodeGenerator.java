package de.twometer.arduleucht.codegen;

import de.twometer.arduleucht.build.BuildException;
import de.twometer.arduleucht.model.Project;

import java.io.*;

public class CodeGenerator {

    public void generateCode(Project project) throws FileNotFoundException, BuildException {
        CodeWriter codeWriter = new CodeWriter();

        project.getMainBlock().writeIncludes(codeWriter);
        project.getMainBlock().writeInit(codeWriter);
        project.getMainBlock().writeExecute(codeWriter);

        String generatedCode = codeWriter.getCode();

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
