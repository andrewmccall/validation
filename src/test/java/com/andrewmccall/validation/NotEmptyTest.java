package com.andrewmccall.validation;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

import javax.validation.ConstraintViolation;
import java.util.Set;

import static junit.framework.Assert.assertTrue;

/**
 * Tests the @NotEmpty annotation
 */
public class NotEmptyTest extends AbstractAnnotationTest {

    @Test
    public void testNull() {
        Set<ConstraintViolation<TestObject>> violations = validator.validate(new TestObject(null));
        assertEquals("We should have a single violation", 1, violations.size());
        assertViolation("test", violations);
    }

    @Test
    public void testEmpty() {
        Set<ConstraintViolation<TestObject>> violations = validator.validate(new TestObject(""));
        assertEquals("We should have a single violation", 1, violations.size());
        assertViolation("test", violations);
    }

    @Test
    public void testSuccess() {
        Set<ConstraintViolation<TestObject>> violations = validator.validate(new TestObject("Test"));
        assertTrue("We should have no violations", violations.isEmpty());
    }

    class TestObject {
        @NotEmpty String test;
        TestObject(String test) {this.test = test;}
    }

}


