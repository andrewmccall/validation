package com.andrewmccall.validation;

import org.apache.commons.lang.StringUtils;

import javax.validation.*;
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
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER})
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = {URL.URLValidator.class})
@ReportAsSingleViolation
public @interface URL {

    static final String regexp = "^(?i:([a-z]+)://)?(?:(\\w+):(\\w+)@)?((?:(?:[-\\w]+\\.)+(?i:com|org|net|gov|mil|biz|info|mobi|name|aero|jobs|museum|travel|[a-z]{2}))|localhost)(?::([\\d]{1,5}))?(?:((?:(?:/+(?:[-\\w~!$+|.,=]|%[a-f\\d]{2})+)+|/+)+))?(?:((?:(?:#(?:[-\\w~!$+|.,=]|%[a-f\\d]{2})+)+|/))+)?((?:(?:\\?(?:[-\\w~!$+|.,*:]|%[a-f\\d{2}])+=(?:[-\\w~!$+|.,*:=]|%[a-f\\d]{2})*)(?:&(?:[-\\w~!$+|.,*:]|%[a-f\\d{2}])+=(?:[-\\w~!$+|.,*:=]|%[a-f\\d]{2})*)*)*(?:#(?:[-\\w~!$+|.,*:=]|%[a-f\\d]{2})*)?)$";

    String message() default "{constraints.InvalidURL}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    /**
     * protocols this annotation accepts.
     */
    String[] protocols() default {"http", "https"};

    /**
     * ignores the annotation if the value is null or empty.
     */
    boolean ignoreEmpty() default true;

    class URLValidator implements ConstraintValidator<URL, String> {

        static final java.util.regex.Pattern pattern = java.util.regex.Pattern.compile(regexp);

        String[] protocols;
        boolean ignoreEmpty;

        public void initialize(URL url) {
            this.protocols = url.protocols();
            this.ignoreEmpty = url.ignoreEmpty();
        }

        public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
            if (s != null) {
                if (ignoreEmpty && StringUtils.trimToNull(s) == null)
                    return true;

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