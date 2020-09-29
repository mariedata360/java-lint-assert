package org.lint.azzert.strategy.decorator;

import org.javatuples.Pair;
import org.lint.azzert.context.AnnotationMetadata;

import java.util.Collection;
import java.util.Set;
import java.util.function.Function;

public class Junit4AnnotationDecorator {

    private final AnnotationMetadata annotation;

    private final Function<Collection<Pair<String, String>>, String> expectedExceptionString = parameters -> {
        if (parameters.size() == 0) return null;
        Pair<String, String> param = parameters.stream().filter( p -> "expected".equals(p.getValue0()) ).findAny().get();
        if (param == null) return null;

        String exceptions = param.getValue(1).toString();
        return exceptions.substring(1, exceptions.length()-1);

    };

    public Junit4AnnotationDecorator(Set<AnnotationMetadata> annotations) {
        //Full signature is `@Test(expected=SomeException.class timeout=XX)`
        //FIXME::use JUnit4Strategy.getSupportedFramework()
        annotation = annotations.stream().filter(a-> "Lorg/junit/Test;".equals(a.getAnnotationName())).findAny().orElse(null);
    }

    public String getExpectedException() {
        if (annotation == null) return null;
        return expectedExceptionString.apply(annotation.getParameters());
    }

    public int getExpectedExceptionLength() {
        if (annotation == null) return 0;
        String exception = expectedExceptionString.apply(annotation.getParameters());
        return exception == null ? 0 : exception.length();
    }
}
