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
import java.util.regex.Matcher;

/**
 * Validates a URL, it's not entierly clever. By default only supports http and https as protocols however they can be
 * added by using the protocls parameter eg @URL(protocols={"http", "https", "ftp", "file"})
 */
@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER })
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = {URL.URLValidator.class})
@ReportAsSingleViolation
public @interface URL {

    static final String regexp = "^(?i:([a-z]+)://)?(?:(\\w+):(\\w+)@)?((?:(?:[-\\w]+\\.)+(?i:com|org|net|gov|mil|biz|info|mobi|name|aero|jobs|museum|travel|[a-z]{2}))|localhost)(?::([\\d]{1,5}))?(?:((?:(?:/+(?:[-\\w~!$+|.,=]|%[a-f\\d]{2})+)+|/+)+))?(?:((?:(?:#(?:[-\\w~!$+|.,=]|%[a-f\\d]{2})+)+|/))+)?((?:(?:\\?(?:[-\\w~!$+|.,*:]|%[a-f\\d{2}])+=(?:[-\\w~!$+|.,*:=]|%[a-f\\d]{2})*)(?:&(?:[-\\w~!$+|.,*:]|%[a-f\\d{2}])+=(?:[-\\w~!$+|.,*:=]|%[a-f\\d]{2})*)*)*(?:#(?:[-\\w~!$+|.,*:=]|%[a-f\\d]{2})*)?)$";

    String message() default "{constraints.InvalidURL}";

    Class<?>[] groups() default {};

    String[] protocols() default {"http", "https"};

    class URLValidator implements ConstraintValidator<URL, Object> {

        static final java.util.regex.Pattern pattern = java.util.regex.Pattern.compile(regexp);
        
        String[] protocols;

        public void initialize(URL url) {
            this.protocols = url.protocols();
        }

        public boolean isValid(Object o, ConstraintValidatorContext constraintValidatorContext) {
            if (o != null) {
                String s = o.toString();
                Matcher m = pattern.matcher(s);
                if (!m.matches())
                    return false;
                String protocol = m.group(1);
                if (protocol != null) {
                    for (String valid : protocols)
                        if (valid.equalsIgnoreCase(protocol)) return true;
                    return false;
                }
            }
            return true;
        }
    }

}