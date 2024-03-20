package ru.nutsalhan87.swt;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

public class Util {
    private static final String[] hex = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D", "E", "F"};

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

    public static String toHex(byte b) {
        return hex[(Byte.toUnsignedInt(b) >> 4) % 16] + hex[Byte.toUnsignedInt(b) % 16];
    }
}
