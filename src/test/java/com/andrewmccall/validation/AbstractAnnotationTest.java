package com.andrewmccall.validation;

import com.andrewmccall.validation.other.ReflectionTestObject;
import com.andrewmccall.validation.other.ReflectionTestObject2;
import org.junit.Before;

import javax.validation.ConstraintViolation;
import javax.validation.ValidatorFactory;
import javax.validation.Validation;
import java.util.Set;

import static junit.framework.Assert.fail;

/**
 * Sets up a validator for some pretty basic tests. There is no custom config, I'm using all defaults.
 */
public abstract class AbstractAnnotationTest<T> {

    protected javax.validation.Validator validator;

    @Before
    public void setup() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    public static <T> void assertViolation(String field, Set<ConstraintViolation<T>> violations) {
        assertViolation(field, null, violations);
    }

    public static <T> void assertViolation(String field, String message, Set<ConstraintViolation<T>> violations) {
        for (ConstraintViolation<T> e : violations)
            if (e.getPropertyPath().iterator().next().getName().equals(field))
                if (message == null || message.equals(e.getMessage()))
                    return;
        fail("The field: " + field + " could not be found in the ConstraintViolations, it's valid.");
    }

    public static <T> void assertValid(String field, Set<ConstraintViolation<T>> violations) {
        for (ConstraintViolation<T> e : violations)
            if (e.getPropertyPath().iterator().next().getName().equals(field))
                fail("The field: " + field + " was invalid");
    }


}
