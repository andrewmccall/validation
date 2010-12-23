package com.andrewmccall.validation;

import org.apache.commons.lang.StringUtils;

import javax.validation.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.lang.annotation.Target;
import java.lang.annotation.Retention;
import java.lang.annotation.Documented;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;


/**
 * Annotation that tests a field is not empty. Empty is defined as:
 * <ul>
 * <li>null</li>
 * <li>"" - a zero length string</li>
 * <li>A collection, map, array with size or length = 0</li>
 * </ul>
 */
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER})
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = {NotEmpty.NotEmptyValidator.class})
@NotNull
@Size(min = 1)
@ReportAsSingleViolation
public @interface NotEmpty {

    String message() default "{constraints.NotEmpty}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    class NotEmptyValidator implements ConstraintValidator<NotEmpty, Object> {

        public void initialize(NotEmpty notEmpty) {
        }

        public boolean isValid(Object o, ConstraintValidatorContext constraintValidatorContext) {
            if (o == null)
                return false;

            if (o instanceof String && StringUtils.isEmpty((String) o)) return false;
            else if (o instanceof Collection && ((Collection) o).isEmpty()) return false;
            else if (o instanceof Map && ((Map) o).isEmpty()) return false;
            else if (o.getClass().isArray() && Array.getLength(o) == 0) return false;
            else
                return true;
        }
    }
}
