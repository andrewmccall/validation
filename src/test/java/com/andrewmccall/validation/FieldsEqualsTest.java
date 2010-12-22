package com.andrewmccall.validation;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

import javax.validation.ConstraintViolation;
import java.util.Set;

import com.andrewmccall.validation.other.ReflectionTestObject;
import com.andrewmccall.validation.other.ReflectionTestObject2;
import static junit.framework.Assert.assertTrue;
import static junit.framework.Assert.fail;

/**
 * Tests the @FieldsEquals annotation.
 */
public class FieldsEqualsTest extends AbstractAnnotationTest {

    @Test
    public void testReflection() {
        Set<ConstraintViolation<ReflectionTestObject>> violations = validator.validate(new ReflectionTestObject("test", "tset"));
        assertEquals("We should have a single violation", 1, violations.size());
        assertErrorsContains("test", violations);

        Set<ConstraintViolation<ReflectionTestObject2>> violations2 = validator.validate(new ReflectionTestObject2("test", "tset"));
        assertEquals("We should have a single violation", 1, violations2.size());
        assertErrorsContains2("test2", violations2);

    }

    @Test
    public void testReflectionSuccess() {
        Set<ConstraintViolation<ReflectionTestObject>> violations = validator.validate(new ReflectionTestObject("test", "test"));
        assertTrue("We should have no violations", violations.isEmpty());
    }

    protected void assertErrorsContains(String field, Set<ConstraintViolation<ReflectionTestObject>> violations) {
        for (ConstraintViolation<ReflectionTestObject> e : violations)
            if (e.getPropertyPath().iterator().next().getName().equals(field))
                    return;
        fail("The field: " + field + " could not be found in the ConstraintViolations:" + violations.toString());
    }

    protected void assertErrorsContains2(String field, Set<ConstraintViolation<ReflectionTestObject2>> violations) {
        for (ConstraintViolation<ReflectionTestObject2> e : violations)
            if (e.getPropertyPath().iterator().next().getName().equals(field))
                    return;
        fail("The field: " + field + " could not be found in the ConstraintViolations:" + violations.toString());
    }

}
