package ru.nutsalhan87.swt.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import ru.nutsalhan87.swt.Util;
import ru.nutsalhan87.swt.domain.exception.NoIdeaException;
import ru.nutsalhan87.swt.domain.exception.TiredException;

import java.lang.reflect.Modifier;
import java.nio.ByteBuffer;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Getter
@AllArgsConstructor
public class Computer {
    MAC mac;

    public <T> Article<T> writeArticle(Human author, Class<T> clazz) throws NoIdeaException, TiredException {
        author.work();
        if (Modifier.isAbstract(clazz.getModifiers()) && !Modifier.isInterface(clazz.getModifiers())) {
            throw new NoIdeaException(author, clazz);
        }
        return new Article<>(author, clazz);
    }

    @Getter
    public static class MAC {
        long address;

        private MAC(long address) {
            this.address = address;
        }

        public static MAC fromLong(long address) {
            return new MAC(address);
        }

        public static MAC random() {
            return new MAC(new Random().nextLong());
        }

        @Override
        public String toString() {
            ByteBuffer buffer = ByteBuffer.allocate(Long.BYTES);
            buffer.putLong(this.address);
            return IntStream
                    .range(0, 8)
                    .skip(2)
                    .mapToObj(i -> Util.toHex(buffer.get(i)))
                    .collect(Collectors.joining("-"));
        }
    }
}
