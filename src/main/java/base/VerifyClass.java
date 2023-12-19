package base;

import org.testng.collections.Lists;

import java.lang.reflect.Array;
import java.util.*;


public class VerifyClass {
    private final Character OPENING_CHARACTER = '[';
    private final Character CLOSING_CHARACTER = ']';

    private final String ASSERT_LEFT = "expected " + OPENING_CHARACTER;
    private final String ASSERT_LEFT2 = "expected not same " + OPENING_CHARACTER;
    private final String ASSERT_LEFT3 = "expected not equals " + OPENING_CHARACTER;
    private final String ASSERT_MIDDLE = CLOSING_CHARACTER + " but found " + OPENING_CHARACTER;
    private final String ASSERT_RIGHT = Character.toString(CLOSING_CHARACTER);

    private final ArrayList<String> results = new ArrayList<String>();

    /**
     * Utility to verify whether any verification is failed
     *
     * @return true if there are any failures else false
     */
    public boolean isFailed() {
        return results.size() > 0;
    }

    /**
     * Utility to verify whether verification passed
     *
     * @return true if there are no failures else false
     */
    public boolean isPassed() {
        return !isFailed();
    }

    /**
     * Get the stored results
     *
     * @return ArrayList of the messages that with failed verifications
     */
    public ArrayList<String> getResults() {
        return results;
    }

    /**
     * Returns the failed messages
     *
     * @return result string
     */
    public String getFailureMessage() {
        return results.toString();
    }

    /**
     * Asserts that a condition is true. If it isn't,
     * an AssertionError, with the given message, is thrown.
     *
     * @param condition the condition to evaluate
     * @param message   the verification error message
     */
    public void verifyTrue(boolean condition, String message) {
        if (!condition) {
            failNotEquals(Boolean.valueOf(condition), Boolean.TRUE, message);
        }
    }

    /**
     * Asserts that a condition is true. If it isn't,
     * an AssertionError is thrown.
     *
     * @param condition the condition to evaluate
     */
    public void verifyTrue(boolean condition) {
        verifyTrue(condition, null);
    }

    /**
     * Asserts that a condition is false. If it isn't,
     * an AssertionError, with the given message, is thrown.
     *
     * @param condition the condition to evaluate
     * @param message   the verification error message
     */
    public void verifyFalse(boolean condition, String message) {
        if (condition) {
            failNotEquals(Boolean.valueOf(condition), Boolean.FALSE, message); // TESTNG-81
        }
    }

    /**
     * Asserts that a condition is false. If it isn't,
     * an AssertionError is thrown.
     *
     * @param condition the condition to evaluate
     */
    public void verifyFalse(boolean condition) {
        verifyFalse(condition, null);
    }

    /**
     * Fails a test with the given message and wrapping the original exception.
     *
     * @param message   the verification error message
     * @param realCause the original exception
     */
    public void fail(String message, Throwable realCause) {
        message += " : " + realCause.getMessage();
        fail(message);
    }

    /**
     * Fails a test with the given message.
     *
     * @param message the verification error message
     */
    public void fail(String message) {
        results.add(message);
    }

    /**
     * Fails a test with no message.
     */
    public void fail() {
        fail("");
    }

    /**
     * Asserts that two objects are equal. If they are not,
     * an AssertionError, with the given message, is thrown.
     *
     * @param actual   the actual value
     * @param expected the expected value
     * @param message  the verification error message
     */
    public void verifyEquals(Object actual, Object expected, String message) {
        Result result = isEqual(actual, expected, message);

        if (!result.passed) {
            if (result.failureMessage.contentEquals(""))
                failNotEquals(actual, expected, message);
            else
                fail(result.failureMessage);
        }
    }

    /**
     * Asserts that two objects are equal. It they are not, an AssertionError,
     * with given message, is thrown.
     *
     * @param actual   the actual value
     * @param expected the expected value (should be an non-null array value)
     * @param message  the verification error message
     */
    private Result verifyArrayEquals(Object actual, Object expected, String message) {
        //is called only when expected is an array
        Result result = new Result();
        if (actual.getClass().isArray()) {
            int expectedLength = Array.getLength(expected);
            if (expectedLength == Array.getLength(actual)) {
                for (int i = 0; i < expectedLength; i++) {
                    Object _actual = Array.get(actual, i);
                    Object _expected = Array.get(expected, i);
                    Result objectResult = isEqual(_actual, _expected);
                    if (!objectResult.passed) {
                        result.passed = false;
                        result.failureMessage = format(actual, expected, message == null ? "" : message
                                + " (values as index " + i + " are not the same)");
                    }
                }
                //array values matched
                return result;
            } else {
                result.passed = false;
                result.failureMessage = format(Array.getLength(actual), expectedLength, message == null ? "" : message
                        + " (Array lengths are not the same)");
                return result;
            }
        }
        result.passed = false;
        result.failureMessage = format(actual, expected, message);
        return result;
    }

    /**
     * Asserts that two objects are equal. If they are not,
     * an AssertionError is thrown.
     *
     * @param actual   the actual value
     * @param expected the expected value
     */
    public void verifyEquals(Object actual, Object expected) {
        verifyEquals(actual, expected, null);
    }

    /**
     * Asserts that two Strings are equal. If they are not,
     * an AssertionError, with the given message, is thrown.
     *
     * @param actual   the actual value
     * @param expected the expected value
     * @param message  the verification error message
     */
    public void verifyEquals(String actual, String expected, String message) {
        verifyEquals((Object) actual, (Object) expected, message);
    }

    /**
     * Asserts that two Strings are equal. If they are not,
     * an AssertionError is thrown.
     *
     * @param actual   the actual value
     * @param expected the expected value
     */
    public void verifyEquals(String actual, String expected) {
        verifyEquals(actual, expected, null);
    }

    /**
     * Asserts that two doubles are equal concerning a delta.  If they are not,
     * an AssertionError, with the given message, is thrown.  If the expected
     * value is infinity then the delta value is ignored.
     *
     * @param actual   the actual value
     * @param expected the expected value
     * @param delta    the absolute tolerate value value between the actual and expected value
     * @param message  the verification error message
     */
    public void verifyEquals(double actual, double expected, double delta, String message) {
        if (!isEqual(actual, expected, delta))
            failNotEquals(new Double(actual), new Double(expected), message);
    }

    /**
     * Asserts that two doubles are equal concerning a delta. If they are not,
     * an AssertionError is thrown. If the expected value is infinity then the
     * delta value is ignored.
     *
     * @param actual   the actual value
     * @param expected the expected value
     * @param delta    the absolute tolerate value value between the actual and expected value
     */
    public void verifyEquals(double actual, double expected, double delta) {
        verifyEquals(actual, expected, delta, null);
    }

    /**
     * Asserts that two floats are equal concerning a delta. If they are not,
     * an AssertionError, with the given message, is thrown.  If the expected
     * value is infinity then the delta value is ignored.
     *
     * @param actual   the actual value
     * @param expected the expected value
     * @param delta    the absolute tolerate value value between the actual and expected value
     * @param message  the verification error message
     */
    public void verifyEquals(float actual, float expected, float delta, String message) {
        if (!isEqual(actual, expected, delta))
            failNotEquals(new Float(actual), new Float(expected), message);
    }

    /**
     * Asserts that two floats are equal concerning a delta. If they are not,
     * an AssertionError is thrown. If the expected
     * value is infinity then the delta value is ignored.
     *
     * @param actual   the actual value
     * @param expected the expected value
     * @param delta    the absolute tolerate value value between the actual and expected value
     */
    public void verifyEquals(float actual, float expected, float delta) {
        verifyEquals(actual, expected, delta, null);
    }

    /**
     * Asserts that two longs are equal. If they are not,
     * an AssertionError, with the given message, is thrown.
     *
     * @param actual   the actual value
     * @param expected the expected value
     * @param message  the verification error message
     */
    public void verifyEquals(long actual, long expected, String message) {
        verifyEquals(Long.valueOf(actual), Long.valueOf(expected), message);
    }

    /**
     * Asserts that two longs are equal. If they are not,
     * an AssertionError is thrown.
     *
     * @param actual   the actual value
     * @param expected the expected value
     */
    public void verifyEquals(long actual, long expected) {
        verifyEquals(actual, expected, null);
    }

    /**
     * Asserts that two booleans are equal. If they are not,
     * an AssertionError, with the given message, is thrown.
     *
     * @param actual   the actual value
     * @param expected the expected value
     * @param message  the verification error message
     */
    public void verifyEquals(boolean actual, boolean expected, String message) {
        verifyEquals(Boolean.valueOf(actual), Boolean.valueOf(expected), message);
    }

    /**
     * Asserts that two booleans are equal. If they are not,
     * an AssertionError is thrown.
     *
     * @param actual   the actual value
     * @param expected the expected value
     */
    public void verifyEquals(boolean actual, boolean expected) {
        verifyEquals(actual, expected, null);
    }

    /**
     * Asserts that two bytes are equal. If they are not,
     * an AssertionError, with the given message, is thrown.
     *
     * @param actual   the actual value
     * @param expected the expected value
     * @param message  the verification error message
     */
    public void verifyEquals(byte actual, byte expected, String message) {
        verifyEquals(Byte.valueOf(actual), Byte.valueOf(expected), message);
    }

    /**
     * Asserts that two bytes are equal. If they are not,
     * an AssertionError is thrown.
     *
     * @param actual   the actual value
     * @param expected the expected value
     */
    public void verifyEquals(byte actual, byte expected) {
        verifyEquals(actual, expected, null);
    }

    /**
     * Asserts that two chars are equal. If they are not,
     * an AssertionFailedError, with the given message, is thrown.
     *
     * @param actual   the actual value
     * @param expected the expected value
     * @param message  the verification error message
     */
    public void verifyEquals(char actual, char expected, String message) {
        verifyEquals(Character.valueOf(actual), Character.valueOf(expected), message);
    }

    /**
     * Asserts that two chars are equal. If they are not,
     * an AssertionError is thrown.
     *
     * @param actual   the actual value
     * @param expected the expected value
     */
    public void verifyEquals(char actual, char expected) {
        verifyEquals(actual, expected, null);
    }

    /**
     * Asserts that two shorts are equal. If they are not,
     * an AssertionFailedError, with the given message, is thrown.
     *
     * @param actual   the actual value
     * @param expected the expected value
     * @param message  the verification error message
     */
    public void verifyEquals(short actual, short expected, String message) {
        verifyEquals(Short.valueOf(actual), Short.valueOf(expected), message);
    }

    /**
     * Asserts that two shorts are equal. If they are not,
     * an AssertionError is thrown.
     *
     * @param actual   the actual value
     * @param expected the expected value
     */
    public void verifyEquals(short actual, short expected) {
        verifyEquals(actual, expected, null);
    }

    /**
     * Asserts that two ints are equal. If they are not,
     * an AssertionFailedError, with the given message, is thrown.
     *
     * @param actual   the actual value
     * @param expected the expected value
     * @param message  the verification error message
     */
    public void verifyEquals(int actual, int expected, String message) {
        verifyEquals(Integer.valueOf(actual), Integer.valueOf(expected), message);
    }

    /**
     * Asserts that two ints are equal. If they are not,
     * an AssertionError is thrown.
     *
     * @param actual   the actual value
     * @param expected the expected value
     */
    public void verifyEquals(int actual, int expected) {
        verifyEquals(actual, expected, null);
    }

    /**
     * Asserts that an object isn't null. If it is,
     * an AssertionError is thrown.
     *
     * @param object the verification object
     */
    public void verifyNotNull(Object object) {
        verifyNotNull(object, null);
    }

    /**
     * Asserts that an object isn't null. If it is,
     * an AssertionFailedError, with the given message, is thrown.
     *
     * @param object  the verification object
     * @param message the verification error message
     */
    public void verifyNotNull(Object object, String message) {
        if (object == null) {
            String formatted = "";
            if (message != null) {
                formatted = message + " ";
            }
            fail(formatted + "expected object to not be null");
        }
        verifyTrue(object != null, message);
    }

    /**
     * Asserts that an object is null. If it is not,
     * an AssertionError, with the given message, is thrown.
     *
     * @param object the verification object
     */
    public void verifyNull(Object object) {
        verifyNull(object, null);
    }

    /**
     * Asserts that an object is null. If it is not,
     * an AssertionFailedError, with the given message, is thrown.
     *
     * @param object  the verification object
     * @param message the verification error message
     */
    public void verifyNull(Object object, String message) {
        if (object != null) {
            failNotSame(object, null, message);
        }
    }

    /**
     * Asserts that two objects refer to the same object. If they do not,
     * an AssertionFailedError, with the given message, is thrown.
     *
     * @param actual   the actual value
     * @param expected the expected value
     * @param message  the verification error message
     */
    public void verifySame(Object actual, Object expected, String message) {
        if (expected == actual) {
            return;
        }
        failNotSame(actual, expected, message);
    }

    /**
     * Asserts that two objects refer to the same object. If they do not,
     * an AssertionError is thrown.
     *
     * @param actual   the actual value
     * @param expected the expected value
     */
    public void verifySame(Object actual, Object expected) {
        verifySame(actual, expected, null);
    }

    /**
     * Asserts that two objects do not refer to the same objects. If they do,
     * an AssertionError, with the given message, is thrown.
     *
     * @param actual   the actual value
     * @param expected the expected value
     * @param message  the verification error message
     */
    public void verifyNotSame(Object actual, Object expected, String message) {
        if (expected == actual) {
            failSame(actual, expected, message);
        }
    }

    /**
     * Asserts that two objects do not refer to the same object. If they do,
     * an AssertionError is thrown.
     *
     * @param actual   the actual value
     * @param expected the expected value
     */
    public void verifyNotSame(Object actual, Object expected) {
        verifyNotSame(actual, expected, null);
    }

    private void failSame(Object actual, Object expected, String message) {
        String formatted = "";
        if (message != null) {
            formatted = message + " ";
        }
        fail(formatted + ASSERT_LEFT2 + expected + ASSERT_MIDDLE + actual + ASSERT_RIGHT);
    }

    private void failNotSame(Object actual, Object expected, String message) {
        String formatted = "";
        if (message != null) {
            formatted = message + " ";
        }
        fail(formatted + ASSERT_LEFT + expected + ASSERT_MIDDLE + actual + ASSERT_RIGHT);
    }

    private void failNotEquals(Object actual, Object expected, String message) {
        fail(format(actual, expected, message));
    }

    private void failEquals(Object actual, Object expected, String message) {
        String formatted = "";
        if (message != null) {
            formatted = message + " ";
        }
        fail(formatted + ASSERT_LEFT3 + expected + ASSERT_MIDDLE + actual + ASSERT_RIGHT);
    }

    String format(Object actual, Object expected, String message) {
        String formatted = "";
        if (null != message) {
            formatted = message + " ";
        }

        return formatted + ASSERT_LEFT + expected + ASSERT_MIDDLE + actual + ASSERT_RIGHT;
    }

    /**
     * Asserts that two collections contain the same elements in the same order. If they do not,
     * an AssertionError is thrown.
     *
     * @param actual   the actual value
     * @param expected the expected value
     */
    public void verifyEquals(Collection actual, Collection expected) {
        verifyEquals(actual, expected, null);
    }

    /**
     * Asserts that two collections contain the same elements in the same order. If they do not,
     * an AssertionError, with the given message, is thrown.
     *
     * @param actual   the actual value
     * @param expected the expected value
     * @param message  the verification error message
     */
    public void verifyEquals(Collection actual, Collection expected, String message) {
        if (actual == expected) {
            return;
        }

        if (actual == null || expected == null) {
            if (message != null) {
                fail(message);
            } else {
                fail("Collections not equal: expected: " + expected + " and actual: " + actual);
            }
            return;
        }

        if (!isEqual(Integer.valueOf(actual.size()), Integer.valueOf(expected.size())).passed) {
            verifyEquals(actual.size(), expected.size(), message + ": lists don't have the same size");
            return;
        }

        Iterator actIt = actual.iterator();
        Iterator expIt = expected.iterator();
        int i = -1;
        while (actIt.hasNext() && expIt.hasNext()) {
            i++;
            Object e = expIt.next();
            Object a = actIt.next();
            String explanation = "Lists differ at element [" + i + "]: " + e + " != " + a;
            String errorMessage = message == null ? explanation : message + ": " + explanation;
            Result objectResult = isEqual(a, e, errorMessage);
            if (!objectResult.passed) {
                fail(objectResult.failureMessage);
                return;
            }
        }
    }

    /**
     * Asserts that two arrays contain the same elements in the same order. If they do not,
     * an AssertionError, with the given message, is thrown.
     *
     * @param actual   the actual value
     * @param expected the expected value
     * @param message  the verification error message
     */
    public void verifyEquals(Object[] actual, Object[] expected, String message) {
        if (actual == expected) {
            return;
        }

        if ((actual == null && expected != null) || (actual != null && expected == null)) {
            if (message != null) {
                fail(message);
            } else {
                fail("Arrays not equal: " + Arrays.toString(expected) + " and " + Arrays.toString(actual));
            }
            return;
        }
        verifyEquals(Arrays.asList(actual), Arrays.asList(expected), message);
    }

    /**
     * Asserts that two arrays contain the same elements in no particular order. If they do not,
     * an AssertionError, with the given message, is thrown.
     *
     * @param actual   the actual value
     * @param expected the expected value
     * @param message  the verification error message
     */
    public void verifyEqualsNoOrder(Object[] actual, Object[] expected, String message) {
        if (actual == expected) {
            return;
        }

        if ((actual == null && expected != null) || (actual != null && expected == null)) {
            failAssertNoEqual(
                    "Arrays not equal: " + Arrays.toString(expected) + " and " + Arrays.toString(actual),
                    message);
        }

        if (actual.length != expected.length) {
            failAssertNoEqual(
                    "Arrays do not have the same size:" + actual.length + " != " + expected.length,
                    message);
        }

        List actualCollection = Lists.newArrayList();
        for (Object a : actual) {
            actualCollection.add(a);
        }
        for (Object o : expected) {
            actualCollection.remove(o);
        }
        if (actualCollection.size() != 0) {
            failAssertNoEqual(
                    "Arrays not equal: " + Arrays.toString(expected) + " and " + Arrays.toString(actual),
                    message);
        }
    }

    private void failAssertNoEqual(String defaultMessage, String message) {
        if (message != null) {
            fail(message);
        } else {
            fail(defaultMessage);
        }
    }

    /**
     * Asserts that two arrays contain the same elements in the same order. If they do not,
     * an AssertionError is thrown.
     *
     * @param actual   the actual value
     * @param expected the expected value
     */
    public void verifyEquals(Object[] actual, Object[] expected) {
        verifyEquals(actual, expected, null);
    }

    /**
     * Asserts that two arrays contain the same elements in no particular order. If they do not,
     * an AssertionError is thrown.
     *
     * @param actual   the actual value
     * @param expected the expected value
     */
    public void verifyEqualsNoOrder(Object[] actual, Object[] expected) {
        verifyEqualsNoOrder(actual, expected, null);
    }

    /**
     * Asserts that two arrays contain the same elements in the same order. If they do not,
     * an AssertionError is thrown.
     *
     * @param actual   the actual value
     * @param expected the expected value
     */
    public void verifyEquals(final byte[] actual, final byte[] expected) {
        verifyEquals(actual, expected, "");
    }

    /**
     * Asserts that two arrays contain the same elements in the same order. If they do not,
     * an AssertionError, with the given message, is thrown.
     *
     * @param actual   the actual value
     * @param expected the expected value
     * @param message  the verification error message
     */
    public void verifyEquals(final byte[] actual, final byte[] expected, final String message) {
        if (expected == actual) {
            return;
        }
        if (null == expected) {
            fail("expected a null array, but not null found. " + message);
            return;
        }
        if (null == actual) {
            fail("expected not null array, but null found. " + message);
            return;
        }

        Result result = isEqual(actual.length, expected.length, "arrays don't have the same size. " + message);
        if (!result.passed) {
            fail(result.failureMessage);
            return;
        }


        for (int i = 0; i < expected.length; i++) {
            if (expected[i] != actual[i]) {
                fail("arrays differ firstly at element [" + i + "]; "
                        + "expected value is <" + expected[i] + "> but was <"
                        + actual[i] + ">. "
                        + message);
                return;
            }
        }
    }

    /**
     * Asserts that two sets are equal.
     */
    public void verifyEquals(Set actual, Set expected) {
        verifyEquals(actual, expected, null);
    }

    /**
     * Assert set equals
     */
    public void verifyEquals(Set<?> actual, Set<?> expected, String message) {
        if (actual == expected) {
            return;
        }

        if (actual == null || expected == null) {
            // Keep the back compatible
            if (message == null) {
                fail("Sets not equal: expected: " + expected + " and actual: " + actual);
                return;
            } else {
                failNotEquals(actual, expected, message);
                return;
            }
        }

        if (!actual.equals(expected)) {
            if (message == null) {
                fail("Sets differ: expected " + expected + " but got " + actual);
                return;
            } else {
                failNotEquals(actual, expected, message);
                return;
            }
        }
    }

    /**
     * Asserts that two maps are equal.
     */
    public void verifyEquals(Map<?, ?> actual, Map<?, ?> expected) {
        if (actual == expected) {
            return;
        }

        if (actual == null || expected == null) {
            fail("Maps not equal: expected: " + expected + " and actual: " + actual);
            return;
        }

        if (actual.size() != expected.size()) {
            fail("Maps do not have the same size:" + actual.size() + " != " + expected.size());
            return;
        }

        Set<?> entrySet = actual.entrySet();
        for (Iterator<?> iterator = entrySet.iterator(); iterator.hasNext(); ) {
            Map.Entry<?, ?> entry = (Map.Entry<?, ?>) iterator.next();
            Object key = entry.getKey();
            Object value = entry.getValue();
            Object expectedValue = expected.get(key);
            Result result = isEqual(value, expectedValue, "Maps do not match for key:" + key + " actual:" + value
                    + " expected:" + expectedValue);
            if (!result.passed) {
                fail(result.failureMessage);
                return;
            }
        }

    }

    /////
    // assertNotEquals
    //

    public void verifyNotEquals(Object actual1, Object actual2, String message) {
        boolean fail = isEqual(actual1, actual2, message).passed;

        if (fail) {
            failEquals(actual1, actual2, message);
        }
    }

    public void verifyNotEquals(Object actual1, Object actual2) {
        verifyNotEquals(actual1, actual2, null);
    }

    void verifyNotEquals(String actual1, String actual2, String message) {
        verifyNotEquals((Object) actual1, (Object) actual2, message);
    }

    void verifyNotEquals(String actual1, String actual2) {
        verifyNotEquals(actual1, actual2, null);
    }

    void verifyNotEquals(long actual1, long actual2, String message) {
        verifyNotEquals(Long.valueOf(actual1), Long.valueOf(actual2), message);
    }

    void verifyNotEquals(long actual1, long actual2) {
        verifyNotEquals(actual1, actual2, null);
    }

    void verifyNotEquals(boolean actual1, boolean actual2, String message) {
        verifyNotEquals(Boolean.valueOf(actual1), Boolean.valueOf(actual2), message);
    }

    void verifyNotEquals(boolean actual1, boolean actual2) {
        verifyNotEquals(actual1, actual2, null);
    }

    void verifyNotEquals(byte actual1, byte actual2, String message) {
        verifyNotEquals(Byte.valueOf(actual1), Byte.valueOf(actual2), message);
    }

    void verifyNotEquals(byte actual1, byte actual2) {
        verifyNotEquals(actual1, actual2, null);
    }

    void verifyNotEquals(char actual1, char actual2, String message) {
        verifyNotEquals(Character.valueOf(actual1), Character.valueOf(actual2), message);
    }

    void verifyNotEquals(char actual1, char actual2) {
        verifyNotEquals(actual1, actual2, null);
    }

    void verifyNotEquals(short actual1, short actual2, String message) {
        verifyNotEquals(Short.valueOf(actual1), Short.valueOf(actual2), message);
    }

    void verifyNotEquals(short actual1, short actual2) {
        verifyNotEquals(actual1, actual2, null);
    }

    void verifyNotEquals(int actual1, int actual2, String message) {
        verifyNotEquals(Integer.valueOf(actual1), Integer.valueOf(actual2), message);
    }

    void verifyNotEquals(int actual1, int actual2) {
        verifyNotEquals(actual1, actual2, null);
    }

    public void verifyNotEquals(float actual1, float actual2, float delta, String message) {
        boolean fail = isEqual(actual1, actual2, delta);

        if (fail) {
            failEquals(actual1, actual2, message);
        }
    }

    public void verifyNotEquals(float actual1, float actual2, float delta) {
        verifyNotEquals(actual1, actual2, delta, null);
    }

    public void verifyNotEquals(double actual1, double actual2, double delta, String message) {
        boolean fail = isEqual(actual1, actual2, delta);

        if (fail) {
            failEquals(actual1, actual2, message);
        }
    }

    public void verifyNotEquals(double actual1, double actual2, double delta) {
        verifyNotEquals(actual1, actual2, delta, null);
    }

    public void assertVerify(String message) {
        if (results.size() > 0) {
            String errorMessage = "";
            if (message != null && !"".contentEquals(message)) {
                errorMessage += message + " : ";
            }
            errorMessage += getFailureMessage();
            throw new AssertionError(errorMessage);
        }
    }

    public void assertVerify() {
        assertVerify(null);
    }

    private boolean isEqual(float actual, float expected, float delta) {
        boolean match = true;
        /* handle infinity specially since subtracting to infinite values gives NaN and
         the following test fails*/
        if (Float.isInfinite(expected)) {
            if (!(expected == actual)) {
                match = false;
            }
        } else if (!(Math.abs(expected - actual) <= delta)) {
            match = false;
        }
        return match;
    }

    private boolean isEqual(double actual, double expected, double delta) {
        boolean match = true;
        /* handle infinity specially since subtracting to infinite values gives NaN and
         the following test fails*/
        if (Double.isInfinite(expected)) {
            if (!(expected == actual)) {
                match = false;
            }
        } else if (!(Math.abs(expected - actual) <= delta)) {
            // Because comparison with NaN always returns false
            match = false;
        }
        return match;
    }

    private Result isEqual(Object actual, Object expected, String message) {
        Result result = new Result();
        if ((expected == null) && (actual == null)) {
            return result;
        }
        if (expected != null) {
            if (expected.getClass().isArray()) {
                result = verifyArrayEquals(actual, expected, message);
                return result;
            } else if (expected.equals(actual)) {
                return result;
            }
        }
        result.passed = false;
        result.failureMessage = format(actual, expected, message);
        return result;
    }

    private Result isEqual(Object actual, Object expected) {
        return isEqual(actual, expected, null);
    }

    private class Result {
        boolean passed = true;
        String failureMessage = "";
    }
}