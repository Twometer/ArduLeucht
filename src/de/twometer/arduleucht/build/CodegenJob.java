package de.twometer.arduleucht.build;

import de.twometer.arduleucht.codegen.CodeGenerator;

import java.io.IOException;

public class CodegenJob implements BuildJob {

    private CodeGenerator generator = new CodeGenerator();

    @Override
    public void execute(ProjectBuilder engine) throws IOException, BuildException {
        generator.generateCode(engine.getProject());
    }

}
