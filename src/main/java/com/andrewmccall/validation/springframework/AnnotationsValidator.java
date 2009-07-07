package com.andrewmccall.validation.springframework;

import org.springframework.validation.Validator;
import org.springframework.validation.Errors;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.Validation;
import javax.validation.ValidatorFactory;
import javax.validation.ConstraintViolation;
import java.util.Set;

/**
 * The validator validates for spring using the JSR-303, it's basically just a thin wrapper that turns
 * ConstraintViolations into Error objects.
 */
@Component
public class AnnotationsValidator implements Validator {

    private javax.validation.Validator validator;
    protected static Logger log = LoggerFactory.getLogger(AnnotationsValidator.class);

    public AnnotationsValidator() {
        if (log.isTraceEnabled()) log.trace("Created new :" + AnnotationsValidator.class.getName());
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Override
    public boolean supports(Class aClass) {
        boolean supports = validator.getConstraintsForClass(aClass).isBeanConstrained();
        if (log.isDebugEnabled())
            log.debug("Does the validator support '" + aClass.getName() + "'? " + supports);
        return supports;

    }

    @Override
    public void validate(Object o, Errors errors) {

        if (log.isTraceEnabled())
            log.trace("Validating: " + o);

        Set<ConstraintViolation<Object>> constraintViolations = validator.validate(o);
        if (log.isDebugEnabled())
            log.debug(constraintViolations.size() + " validation errors with object " + o);
        for (ConstraintViolation violation : constraintViolations) {
            String fieldPath = violation.getPropertyPath();
            String errorCode = violation.getMessage();
            if (log.isTraceEnabled())
                log.trace("Rejecting field: " + fieldPath + " with error " + errorCode);
            errors.rejectValue(fieldPath, errorCode);
        }
    }

}
