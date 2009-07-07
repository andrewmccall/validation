package com.andrewmccall.validation;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.ReportAsSingleViolation;
import javax.validation.constraints.Pattern;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Validates an Email address including the TLD against a list of known TLDs. The validation where possibe tries to be
 * as accurate and will accept some vary odd looking email addresses that conform to the proper RFCs.
 */
@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER })
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = {Email.EmailValidator.class})
@Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+(?:[A-Z]{2}|com|org|net|gov|mil|biz|info|mobi|name|aero|jobs|museum)\\b", message="pattern.mismatch")
@ReportAsSingleViolation
public @interface Email {

    String message() default "{constraints.InvalidEmail}";

    Class<?>[] groups() default {};

    class EmailValidator implements ConstraintValidator<Email, Object> {

        public void initialize(Email email) {}

        public boolean isValid(Object o, ConstraintValidatorContext constraintValidatorContext) {
            return true;
        }
    }

}
