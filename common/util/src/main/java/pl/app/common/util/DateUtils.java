package pl.app.common.util;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DateUtils {
    public static boolean isDataRangeNoCollide(LocalDate testedStart, LocalDate testedEnd, LocalDate targetStart, LocalDate targetEnd) {
        if (testedStart == null || testedEnd == null || targetStart == null || targetEnd == null) {
            throw DateUtilsValidationException.fromNull();
        }
        if (isAfter(testedStart, testedEnd) || isAfter(targetStart, targetEnd)) {
            throw new DateUtilsValidationException();
        }
        if (isBeforeOrEquals(testedStart, targetStart) && isBeforeOrEquals(testedStart, testedEnd) && isBeforeOrEquals(testedEnd, targetStart)) {
            return true;
        }
        return isAfterOrEquals(testedStart, targetStart) && isAfterOrEquals(testedEnd, testedEnd) && isAfterOrEquals(testedStart, targetEnd);
    }

    public static boolean isDataRangeCollide(LocalDate testedStart, LocalDate testedEnd, LocalDate targetStart, LocalDate targetEnd) {
        return !isDataRangeNoCollide(testedStart, testedEnd, targetStart, targetEnd);
    }

    public static List<LocalDate> getRangeFromDates(LocalDate start, LocalDate end) {
        if (start == null || end == null) {
            throw DateUtilsValidationException.fromNull();
        }
        List<LocalDate> dateRange = new ArrayList<>();
        LocalDate current = start;

        while (!isAfter(current, end)) {
            dateRange.add(current);
            current = current.plusDays(1); // Increment by one day
        }

        return dateRange;
    }

    public static boolean isBetweenOrEquals(LocalDate tested, LocalDate targetStart, LocalDate targetEnd) {
        if (tested == null || targetStart == null || targetEnd == null) {
            throw DateUtilsValidationException.fromNull();
        }
        return isAfterOrEquals(tested, targetStart) && isBeforeOrEquals(tested, targetEnd);
    }

    public static boolean isBeforeOrEquals(LocalDate tested, LocalDate target) {
        if (tested == null || target == null) {
            throw DateUtilsValidationException.fromNull();
        }
        return tested.compareTo(target) <= 0;
    }

    public static boolean isBefore(LocalDate tested, LocalDate target) {
        if (tested == null || target == null) {
            throw DateUtilsValidationException.fromNull();
        }
        return tested.compareTo(target) < 0;
    }

    public static boolean isAfterOrEquals(LocalDate tested, LocalDate target) {
        if (tested == null || target == null) {
            throw DateUtilsValidationException.fromNull();
        }
        return tested.compareTo(target) >= 0;
    }

    public static boolean isAfter(LocalDate tested, LocalDate target) {
        if (tested == null || target == null) {
            throw DateUtilsValidationException.fromNull();
        }
        return tested.compareTo(target) > 0;
    }

    public static boolean isEquals(LocalDate tested, LocalDate target) {
        if (tested == null || target == null) {
            throw DateUtilsValidationException.fromNull();
        }
        return tested.compareTo(target) == 0;
    }

    public static class DateUtilsValidationException extends RuntimeException {
        public DateUtilsValidationException() {
            super("Argument is not valid");
        }

        public DateUtilsValidationException(String message) {
            super(message);
        }

        public static DateUtilsValidationException fromNull() {
            return new DateUtilsValidationException("Argument is mull");
        }
    }
}
