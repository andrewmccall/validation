package com.andrewmccall.validation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.junit.Before;

import javax.validation.ValidatorFactory;
import javax.validation.Validation;

/**
 * Sets up a validator for some pretty basic tests. There is no custom config, I'm using all defaults.
 */
public abstract class AbstractAnnotationTest {

    protected javax.validation.Validator validator;

    protected Logger log = LoggerFactory.getLogger(this.getClass());

    @Before
    public void setup() {
        if (log.isTraceEnabled()) log.trace("Created new :" + this.getClass().getName());
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }
}
