package de.twometer.arduleucht.codegen;

import de.twometer.arduleucht.model.BuildJob;
import de.twometer.arduleucht.model.Project;

import java.io.*;

public class CodeGenerator {

    public void generateCode(BuildJob job) throws FileNotFoundException {
        Project project = job.getProject();
        CodeWriter codeWriter = new CodeWriter();

        project.getMainBlock().writeIncludes(codeWriter);
        project.getMainBlock().writeInit(codeWriter);
        project.getMainBlock().writeExecute(codeWriter);

        String generatedCode = codeWriter.getCode();

        File genFile = new File(project.getProjectFolder(), "generated.ino");

        if (!tryDelete(genFile)) {
            job.fail("Could not delete previously generated .ino file");
            return;
        }

        PrintWriter writer = new PrintWriter(new FileOutputStream(genFile));
        writer.write(generatedCode);
        writer.close();
    }

    private boolean tryDelete(File genFile) {
        return !genFile.exists() || genFile.delete();
    }

}
