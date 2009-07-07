package com.andrewmccall.validation.springframework;

import javax.validation.constraints.NotNull;

/**
 * Test bean used to test validation.
 */
public class Child {

    String string = null;

    @NotNull(message="string.notnull")
    public String getString() {
        return string;
    }

}