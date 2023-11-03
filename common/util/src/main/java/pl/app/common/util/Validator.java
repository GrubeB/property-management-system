package pl.app.common.util;

public class Validator {
    private static final String NOT_NULL_DEFAULT_MESSAGE = "Object cannot be null";
    private static final String NOT_EMPTY_DEFAULT_MESSAGE = "String cannot be empty";
    private static final String NOT_BLANK_DEFAULT_MESSAGE = "String cannot be blank";
    private static final String IS_TRUE_DEFAULT_MESSAGE = "Condition cannot be false";
    private static final String IS_FALSE_DEFAULT_MESSAGE = "Condition cannot be true";
    private static final String CONTAINS_NO_NULLS_DEFAULT_MESSAGE = "Collection cannot contains null values";

    public static void notNull(final Object object) {
        notNull(object, NOT_NULL_DEFAULT_MESSAGE);
    }

    public static void notNull(final Object object, final String message) {
        if (object == null) {
            throw new IllegalArgumentException(message);
        }
    }

    public static void notEmpty(final String object) {
        notEmpty(object, NOT_EMPTY_DEFAULT_MESSAGE);
    }

    public static void notEmpty(final String object, final String message) {
        if (object == null || object.isEmpty()) {
            throw new IllegalArgumentException(message);
        }
    }
    public static void notBlank(final String object){
        notBlank(object, NOT_BLANK_DEFAULT_MESSAGE);
    }
    public static void notBlank(final String object, final String message) {
        if (object == null || object.isBlank()) {
            throw new IllegalArgumentException(message);
        }
    }
    public static void isTrue(final boolean condition){
        isTrue(condition, IS_TRUE_DEFAULT_MESSAGE);
    }
    public static void isTrue(final boolean condition, final String message) {
        if (!condition) {
            throw new IllegalArgumentException(message);
        }
    }
    public static void isFalse(final boolean condition){
        isFalse(condition, IS_FALSE_DEFAULT_MESSAGE);
    }
    public static void isFalse(final boolean condition, final String message) {
        if (condition) {
            throw new IllegalArgumentException(message);
        }
    }
    public static void containsNoNulls(final Iterable<?> collection){
        containsNoNulls(collection,CONTAINS_NO_NULLS_DEFAULT_MESSAGE);
    }
    public static void containsNoNulls(final Iterable<?> collection, final String message) {
        for (final Object object : collection) {
            notNull(object, message);
        }
    }
}
