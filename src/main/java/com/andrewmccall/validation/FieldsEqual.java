package com.andrewmccall.validation;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import javax.validation.Constraint;
import javax.validation.ReportAsSingleViolation;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.annotation.Target;
import java.lang.annotation.Retention;
import java.lang.annotation.Documented;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import static java.lang.annotation.ElementType.TYPE;
import java.lang.reflect.Field;

/**
 * Annotation that checks that two fields in a given object are .equal().
 */
@Target({TYPE})
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = {FieldsEqual.FieldsEqualValidator.class})
@ReportAsSingleViolation
public @interface FieldsEqual {

    String message() default "{constraints.FieldsEqual}";

    Class<?>[] groups() default {};

    String[] fields() default {};

    class FieldsEqualValidator implements ConstraintValidator<FieldsEqual, Object> {

        static Logger log = LoggerFactory.getLogger(FieldsEqualValidator.class);

        String[] fields;

        public void initialize(FieldsEqual fieldsEqual) {
            this.fields = fieldsEqual.fields();
        }

        public boolean isValid(Object o, ConstraintValidatorContext constraintValidatorContext) {
            if (o == null)
                return true;

            Object value = null;
            for (String field : fields ) {
                if (log.isDebugEnabled())
                    log.debug("Testing field " + field);
                try {
                    Field f = o.getClass().getDeclaredField(field);
                    if (value == null)
                        value = f.get(o);
                    else if (!value.equals(f.get(o)))
                        return false;
                } catch (NoSuchFieldException e) {
                    throw new RuntimeException("No such field " + field, e);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException("Can't access field " + field, e);
                }
            }
            return true;
        }
    }
}
