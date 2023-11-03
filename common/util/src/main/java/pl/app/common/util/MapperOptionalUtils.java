package pl.app.common.util;

import java.util.Optional;

public class MapperOptionalUtils {
    public static <T> T map(Optional<T> optional) {
        return optional.orElse(null);
    }
    public static <T> Optional<T> map(T obj) {
        return Optional.ofNullable(obj);
    }
}
