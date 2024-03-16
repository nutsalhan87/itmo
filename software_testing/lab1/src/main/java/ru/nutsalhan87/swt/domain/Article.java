package ru.nutsalhan87.swt.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class Article<T> {
    @Getter
    Human author;
    Class<T> clazz;

    @Override
    public String toString() {
        return clazz.getSimpleName();
    }
}
