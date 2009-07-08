package com.andrewmccall.validation.other;

import com.andrewmccall.validation.FieldsEqual;

/**
 * Another test object to test some of the FieldsEqual functionality.
 */
@FieldsEqual(fields={"test", "test2"}, errorField="test2")
public class ReflectionTestObject2 {
    private String test;
    private String test2;

    public ReflectionTestObject2(String test, String test2) {
        this.test = test;
        this.test2 = test2;
    }

    public String getTest() {
        return test;
    }

    public void setTest(String test) {
        this.test = test;
    }

    public String getTest2() {
        return test2;
    }

    public void setTest2(String test2) {
        this.test2 = test2;
    }
}
