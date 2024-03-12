package ru.nutsalhan87.swt;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

public class Util {
    @AllArgsConstructor
    @Getter
    @EqualsAndHashCode
    public static class Enumerated<T> {
        private int index;
        private T object;
    }

    public static <T> Stream<Enumerated<T>> enumerate(Stream<T> stream) {
        AtomicInteger i = new AtomicInteger();
        return stream.map(v -> new Enumerated<>(i.getAndIncrement(), v));
    }
}
