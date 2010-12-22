package com.andrewmccall.validation;

import org.junit.Before;

import javax.validation.ValidatorFactory;
import javax.validation.Validation;

/**
 * Sets up a validator for some pretty basic tests. There is no custom config, I'm using all defaults.
 */
public abstract class AbstractAnnotationTest {

    protected javax.validation.Validator validator;

    @Before
    public void setup() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }
}
