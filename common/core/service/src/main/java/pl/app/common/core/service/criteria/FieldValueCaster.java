package pl.app.common.core.service.criteria;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.time.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class FieldValueCaster {
    public final static FieldValueCaster INSTANCE = new FieldValueCaster();

    public Object castValueTo(String value, Class<?> fieldType) {
        if (value == null || fieldType == null) {
            throw new IllegalArgumentException("Value and type cannot be null.");
        }

        if (fieldType == String.class) {
            return value;
        }else if (fieldType == UUID.class) {
            return UUID.fromString(value);
        }
        // primitive values and wrappers
        else if (fieldType == Character.class || fieldType == char.class) {
            return value.charAt(0);
        } else if (fieldType == Short.class || fieldType == short.class) {
            return Short.valueOf(value);
        } else if (fieldType == Integer.class || fieldType == int.class) {
            return Integer.valueOf(value);
        } else if (fieldType == Long.class || fieldType == long.class) {
            return Long.valueOf(value);
        } else if (fieldType == Float.class || fieldType == float.class) {
            return Float.parseFloat(value);
        } else if (fieldType == Double.class || fieldType == double.class) {
            return Double.parseDouble(value);
        } else if (fieldType == Boolean.class || fieldType == boolean.class) {
            return Boolean.parseBoolean(value);
        }
        // date and time
        else if (fieldType == LocalTime.class) {
            return LocalTime.parse(value);
        } else if (fieldType == LocalDate.class) {
            return LocalDate.parse(value);
        } else if (fieldType == LocalDateTime.class) {
            return LocalDateTime.parse(value);
        } else if (fieldType == Instant.class) {
            return Instant.parse(value);
        } else if (fieldType == OffsetDateTime.class) {
            return OffsetDateTime.parse(value);
        } else if (fieldType == ZonedDateTime.class) {
            return ZonedDateTime.parse(value);
        }
        // enum
        else if (fieldType.isEnum()) {
            @SuppressWarnings("unchecked")
            Object object = Enum.valueOf((Class) fieldType, value);
            return object;
        } else {
            // For custom types, attempt to find a constructor that takes a String as an argument.
            try {
                Constructor<?> constructor = fieldType.getConstructor(String.class);
                return constructor.newInstance(value);
            } catch (NoSuchMethodException | InstantiationException | IllegalAccessException |
                     InvocationTargetException e) {
                throw new IllegalArgumentException("No constructor found in " + fieldType.getName() + " that takes a String as an argument.");
            }
        }
    }

    public Object casValuesTo(List<String> value, Class<?> fieldType) {
        List<Object> lists = new ArrayList<>();
        for (String s : value) {
            lists.add(castValueTo(s, fieldType));
        }
        return lists;
    }
}
