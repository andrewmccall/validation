package com.andrewmccall.validation;

import static org.apache.commons.beanutils.PropertyUtils.getProperty;

import org.apache.commons.lang.StringUtils;

import javax.validation.*;
import java.lang.annotation.Target;
import java.lang.annotation.Retention;
import java.lang.annotation.Documented;

import static java.lang.annotation.RetentionPolicy.RUNTIME;
import static java.lang.annotation.ElementType.TYPE;

import java.lang.reflect.InvocationTargetException;

/**
 * Annotation that checks that two fields in a given object are .equal().
 */
@Target({TYPE})
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = {FieldsEqual.FieldsEqualValidator.class})
public @interface FieldsEqual {

    String message() default "{constraints.FieldsEqual}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String[] fields();

    String errorField() default "";

    class FieldsEqualValidator implements ConstraintValidator<FieldsEqual, Object> {

        String[] fields;
        String errorField;

        public void initialize(FieldsEqual fieldsEqual) {
            this.fields = fieldsEqual.fields();
            if (StringUtils.trimToNull(fieldsEqual.errorField()) != null)
                this.errorField = fieldsEqual.errorField();
            else
                this.errorField = fields[0];
        }

        public boolean isValid(Object o, ConstraintValidatorContext constraintValidatorContext) {
            if (o == null)
                return true;

            Object value = null;
            for (String field : fields) {
                try {
                    if (value == null)

                        value = getProperty(o, field);

                    else if (!value.equals(getProperty(o, field))) {
                        constraintValidatorContext.disableDefaultConstraintViolation();
                        constraintValidatorContext.buildConstraintViolationWithTemplate("message")
                                .addNode(errorField)
                                .addConstraintViolation();
                        return false;

                    }
                } catch (Exception e) {
                    throw new RuntimeException("Error getting property", e);
                }

            }
            return true;
        }
    }
}
