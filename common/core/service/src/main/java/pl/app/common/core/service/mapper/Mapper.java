package pl.app.common.core.service.mapper;

import java.util.AbstractMap;
import java.util.Map;
import java.util.function.Function;

@SuppressWarnings("unchecked")
public interface Mapper {
    default <T, R> R map(T source, Class<R> destinationClass) {
        Class<T> sourceClass = (Class<T>) source.getClass();
        Function<T, R> mapper = getMapper(sourceClass, destinationClass);
        if (mapper == null) {
            throw new RuntimeException("Mapper " + getClass().getName() + " has no mapper from " + sourceClass.getName() + " to " + destinationClass.getName());
        }
        return mapper.apply(source);
    }

    default <T, R> Function<T, R> getMapper(Class<T> sourceClass, Class<R> destinationClass) {
        AbstractMap.SimpleEntry<Class<T>, Class<R>> key = new AbstractMap.SimpleEntry<>(sourceClass, destinationClass);
        return (Function<T, R>) getMappers().get(key);
    }

    default <T, U> void addMapper(Class<T> source, Class<U> destination, Function<T, U> function) {
        getMappers().put(new AbstractMap.SimpleEntry<>(source, destination), function);
    }

    Map<AbstractMap.SimpleEntry<Class<?>, Class<?>>, Function<?, ?>> getMappers();
}
