package org.lint.azzert.command.output;

import org.lint.azzert.OutputFormatterCommand;
import org.lint.azzert.context.MethodMetadata;

import java.util.Set;

public class OutputOnlyAssertlessMethods implements OutputFormatterCommand<Void> {

    @Override
    public Void execute(Set<MethodMetadata> testMethods) {
        testMethods.removeIf(m -> !m.getMethodCalls().isEmpty());
        return null;
    }
}
