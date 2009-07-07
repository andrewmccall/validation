package com.andrewmccall.validation.springframework;

import javax.validation.constraints.NotNull;
import javax.validation.Valid;

/**
 * Test bean used to test validation.
 */
public class Parent {

    String name = null;
    Child child;

    @NotNull(message="name.notnull")
    public String getName() {
        return name;
    }

    @NotNull(message="child.notnull")
    @Valid
    public Child getChild() {
        return child;
    }

}
