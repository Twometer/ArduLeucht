package de.twometer.arduleucht.build.jobs;

import de.twometer.arduleucht.build.BuildException;
import de.twometer.arduleucht.build.ProjectBuilder;
import de.twometer.arduleucht.build.codegen.CodeGenerator;

import java.io.IOException;

public class CodegenJob implements BuildJob {

    private CodeGenerator generator = new CodeGenerator();

    @Override
    public void execute(ProjectBuilder engine) throws IOException, BuildException {
        generator.generateCode(engine.getProject());
    }

}
