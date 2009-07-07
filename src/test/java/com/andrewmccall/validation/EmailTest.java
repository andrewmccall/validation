package com.andrewmccall.validation;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

import javax.validation.ConstraintViolation;
import java.util.Set;

import static junit.framework.Assert.assertTrue;

/**
 * Tests the @Email annotation
 */
public class EmailTest extends AbstractAnnotationTest {

    @Test
    public void testSuccessForNull() {
        Set<ConstraintViolation<TestObject>> violations = validator.validate(new TestObject(null));
        assertTrue("We're not testing for null, should have no violations", violations.isEmpty());
    }

    @Test
    public void testNormalStringFails() {
        assertViolation("test");
    }

    @Test
    public void testNoTLDFails() {
        assertViolation("test@test");
    }

    @Test
    public void testUnknownTLDFails() {
        assertViolation("test@test.test");
    }

    @Test
    public void testSuccess() {
        Set<ConstraintViolation<TestObject>> violations = validator.validate(new TestObject("firstname.lastname@test.com"));
        assertTrue("We're not testing for null, should have no violations", violations.isEmpty());
    }
    
    private void assertViolation(String test) {
        Set<ConstraintViolation<TestObject>> violations = validator.validate(new TestObject(test));
        assertEquals("Should have a single violation for the invalid email '" + test + "'", 1, violations.size());
    }

    class TestObject {
        @Email String email;
        TestObject(String email) {this.email = email;}
    }

}
