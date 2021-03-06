package com.andrewmccall.validation;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

import javax.validation.ConstraintViolation;
import java.util.Set;

import static junit.framework.Assert.assertTrue;
import static junit.framework.Assert.assertFalse;

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
    public void testSuccessForEmpty() {
        Set<ConstraintViolation<TestObject>> violations = validator.validate(new TestObject(""));
        assertTrue("We're not testing for \"\", should have no violations", violations.isEmpty());

        class TestObject2 {
            @Email(ignoreEmpty = false)
            String email;

            TestObject2(String email) {
                this.email = email;
            }
        }
        Set<ConstraintViolation<TestObject2>> violations2 = validator.validate(new TestObject2(""));
        assertFalse("We're testing for \"\", should have violations", violations2.isEmpty());

    }

    @Test
    public void testNormalStringFails() {
        assertViolation("email", validator.validate(new TestObject("test")));
    }

    @Test
    public void testNoTLDFails() {
        assertViolation("email", validator.validate(new TestObject("test@test")));
    }

    @Test
    public void testUnknownTLDFails() {
        assertViolation("email", validator.validate(new TestObject("test@test.test")));
    }
    
    @Test
    public void testTwoLetterTLD() {
        assertValid("email", validator.validate(new TestObject("test@test.co.uk")));
    }

    @Test
    public void testSuccess() {
    	assertValid("email", validator.validate(new TestObject("firstname.lastname@test.com")));
    }
    
    class TestObject {
        @Email
        String email;

        TestObject(String email) {
            this.email = email;
        }
    }

}
