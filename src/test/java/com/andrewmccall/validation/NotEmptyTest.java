package com.andrewmccall.validation;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

import javax.validation.ConstraintViolation;
import java.util.*;

import static junit.framework.Assert.assertTrue;

/**
 * Tests the @NotEmpty annotation
 */
public class NotEmptyTest extends AbstractAnnotationTest {

    @Test
    public void testNull() {
        Set<ConstraintViolation<TestStringObject>> violations = validator.validate(new TestStringObject(null));
        assertEquals("We should have a single violation", 1, violations.size());
        assertViolation("test", violations);
    }

    @Test
    public void testEmpty() {
        assertViolation("test", validator.validate(new TestStringObject("")));
        assertViolation("test", validator.validate(new TestArrayObject(new byte[0])));
        assertViolation("test", validator.validate(new TestCollectionObject(new ArrayList())));
        assertViolation("test", validator.validate(new TestMapObject(new HashMap())));
    }

    @Test
    public void testSuccess() {
        assertValid("test", validator.validate(new TestStringObject("test")));
        assertValid("test", validator.validate(new TestArrayObject(new byte[]{0x1})));

        ArrayList list = new ArrayList();
        list.add("test");
        assertValid("test", validator.validate(new TestCollectionObject(list)));

        HashMap map = new HashMap();
        map.put("blah", "blah");
        assertValid("test", validator.validate(new TestMapObject(map)));
    }

    class TestStringObject {
        @NotEmpty String test;
        TestStringObject(String test) {this.test = test;}
    }

    class TestArrayObject {
        @NotEmpty byte[] test;
        TestArrayObject(byte[] test) {this.test = test;}
    }

    class TestCollectionObject {
        @NotEmpty Collection test;
        TestCollectionObject(Collection test) {this.test = test;}
    }

    class TestMapObject {
        @NotEmpty Map test;
        TestMapObject(Map test) {this.test = test;}
    }

}


