package com.andrewmccall.validation.springframework;

import static junit.framework.Assert.assertTrue;
import static junit.framework.Assert.assertFalse;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.assertEquals;
import org.springframework.validation.BindException;
import org.springframework.validation.Errors;

/**
 * Tests the very basic operation of the spring Validator.
 */
public class AnnotationsValidatorTest  {

    AnnotationsValidator validator;
    Errors errors;

    @Before
    public void setup() {
        validator = new AnnotationsValidator();
    }

    @Test
    public void testSupports() {
        assertTrue("The validator should support the Parent object", validator.supports(Parent.class));
        assertTrue("The validator should support the Child object", validator.supports(Child.class));
        assertFalse("The validator should not support the Nothing object.", validator.supports(Nothing.class));
    }

    @Test
    public void testParentValidation() {
        Parent p = new Parent();
        errors = new BindException(p, "parent");
        validator.validate(p, errors);
        assertEquals("There should be two errors", 2, errors.getErrorCount());

        assertEquals("The errors should be on field: name", 1, errors.getFieldErrorCount("name"));
        assertEquals("The error message should be: name.notnull", "name.notnull", errors.getFieldError("name").getCode());

        assertEquals("The errors should be on field: child", 1, errors.getFieldErrorCount("child"));
        assertEquals("The error message should be: name.notnull", "child.notnull", errors.getFieldError("child").getCode());
    }

    @Test
    public void testInvlidChild() {
        Parent p = new Parent();
        p.name = "Test";
        p.child = new Child();
        errors = new BindException(p, "parent");
        validator.validate(p, errors);
        assertEquals("There should be one errors", 1, errors.getErrorCount());
        assertEquals("The errors should be on field: child.string", 1, errors.getFieldErrorCount("child.string"));
        assertEquals("The error message should be: child.string", "string.notnull", errors.getFieldError("child.string").getCode());
    }

    @Test
    public void testChild () {
        Parent p = new Parent();
        p.name = "Test";
        p.child = new Child();
        p.child.string = "Test";
        errors = new BindException(p, "parent");
        validator.validate(p, errors);
        assertEquals("There should be no errors", 0, errors.getErrorCount());
    }

}

class Nothing {
    String name = null;

    public String getName() {
        return name;
    }
}