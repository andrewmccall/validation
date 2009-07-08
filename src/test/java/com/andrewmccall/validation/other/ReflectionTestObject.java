package com.andrewmccall.validation.other;

import com.andrewmccall.validation.FieldsEqual;

/**
 * Used to test that reflection is working outside of the same pacakge as teh @FieldsEqual
 */
@FieldsEqual(fields={"test", "test2"})
public class ReflectionTestObject {

    private String test;
    private String test2;

    public ReflectionTestObject(String test, String test2) {
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
