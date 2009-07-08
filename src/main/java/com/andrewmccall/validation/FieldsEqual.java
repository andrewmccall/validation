package com.andrewmccall.validation;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.apache.commons.beanutils.PropertyUtils;
import static org.apache.commons.beanutils.PropertyUtils.getProperty;
import org.apache.commons.lang.StringUtils;

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
import java.lang.reflect.InvocationTargetException;

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

    String[] fields();

    String errorField() default "";

    class FieldsEqualValidator implements ConstraintValidator<FieldsEqual, Object> {

        static Logger log = LoggerFactory.getLogger(FieldsEqualValidator.class);

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

            constraintValidatorContext.addError(constraintValidatorContext.getDefaultErrorMessage(), errorField);
            constraintValidatorContext.disableDefaultError();

            Object value = null;
            for (String field : fields ) {
                if (log.isDebugEnabled())
                    log.debug("Testing field " + field);
                try {

                    if (value == null)
                        value = getProperty(o, field);
                    else if (!value.equals(getProperty(o, field)))
                        return false;
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e.getMessage(), e);
                } catch (NoSuchMethodException e) {
                    throw new RuntimeException(e.getMessage(), e);
                } catch (InvocationTargetException e) {
                    throw new RuntimeException(e.getMessage(), e);
                }
            }
            return true;
        }
    }
}
