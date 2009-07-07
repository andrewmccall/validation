package com.andrewmccall.validation;

import org.junit.Test;

import javax.validation.ConstraintViolation;
import java.util.Set;
import java.util.regex.Pattern;

import static junit.framework.Assert.assertTrue;
import static junit.framework.Assert.assertFalse;

/**
 * Make sure the URL validation does what it's meant to be doing
 */
public class URLTest extends AbstractAnnotationTest {

    Pattern urlPattern = Pattern.compile(URL.regexp);

    @Test
    public void testSuccessForNull() {
        Set<ConstraintViolation<TestObject>> violations = validator.validate(new TestObject(null));
        assertTrue("We're not testing for null, should have no violations", violations.isEmpty());
    }

    @Test
    public void testValidURLs() {
        String url = "http://andrewmccall.com";
        assertValidPattern(url);
        assertNoViolations(url);

        url = "http://www.andrewmccall.com";
        assertValidPattern(url);
        assertNoViolations(url);

        url = "http://random.andrewmccall.com";
        assertValidPattern(url);
        assertNoViolations(url);

        url = "http://andrewmccall.com/";
        assertValidPattern(url);
        assertNoViolations(url);

        url = "http://andrewmccall.com/index.html";
        assertValidPattern(url);
        assertNoViolations(url);

        url = "HTTP://andrewmccall.com/index.html";
        assertValidPattern(url);
        assertNoViolations(url);

        url = "http://AndrewMcCall.com/index.html";
        assertValidPattern(url);
        assertNoViolations(url);

        url = "http://andrewmccall.COM/index.html";
        assertValidPattern(url);
        assertNoViolations(url);

        url = "http://andrew:test@www.andrewmccall.com:1234/some/dir/index.html#result?foo=bar&foo2=bar2";
        assertValidPattern(url);
        assertNoViolations(url);

        url = "http://localhost";
        assertValidPattern(url);
        assertNoViolations(url);

        url = "andrewmccall.com";
        assertValidPattern(url);
        assertNoViolations(url);

        url = "andrewmccall.com/some/dir/index.html#result?foo=bar&foo2=bar2";
        assertValidPattern(url);
        assertNoViolations(url);

    }

    @Test
    public void testUnknownTLD () {
        String url = "http://andrew.mccall";
        assertInvalidPattern(url);
        assertViolations(url);
    }

    @Test
    public void testCustomProtocol () {
        class TestObject2 {
            @URL(protocols={"http", "custom"}) String url;
            TestObject2(String url) { this.url = url; }
        }

        String url = "http://andrewmccall.com";
        assertValidPattern(url);
        Set<ConstraintViolation<TestObject2>> violations = validator.validate(new TestObject2(url));
        assertTrue("Should have no violations (" + url + ")", violations.isEmpty());

        url = "custom://andrewmccall.com";
        assertValidPattern(url);
        violations = validator.validate(new TestObject2(url));
        assertTrue("Should have no violations (" + url + ")", violations.isEmpty());

    }

    /**
     * For an invalid protocol the URL should match the pattern, but it should throw violations.
     */
    @Test
    public void testBadProtocol () {
        String url = "ftp://andrewmccall.com";
        assertValidPattern(url);
        assertViolations(url);
    }

    private void assertValidPattern(String url) {
        assertTrue("The url should be valid (" + url +")", urlPattern.matcher(url).matches());
    }

    private void assertNoViolations(String url) {
        Set<ConstraintViolation<TestObject>> violations = validator.validate(new TestObject(url));
        assertTrue("Should have no violations (" + url + ")", violations.isEmpty());
    }

    private void assertInvalidPattern(String url) {
        assertFalse("The url should not be valid  (" + url + ")", urlPattern.matcher(url).matches());
    }

    private void assertViolations(String url) {
        Set<ConstraintViolation<TestObject>> violations = validator.validate(new TestObject(url));
        assertFalse("Should have violations (" + url + ")", violations.isEmpty());
    }

    class TestObject {
        @URL String url;
        TestObject(String url) { this.url = url; }
    }
}
