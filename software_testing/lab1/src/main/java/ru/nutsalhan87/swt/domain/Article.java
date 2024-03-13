package ru.nutsalhan87.swt.domain;

import lombok.Getter;

import java.lang.reflect.Modifier;

public class Article<T> {
    @Getter
    Human author;
    Class<T> clazz;

    public Article(Human author, Class<T> clazz) throws NoIdeaException {
        if (Modifier.isAbstract(clazz.getModifiers()) && !Modifier.isInterface(clazz.getModifiers())) {
            throw new NoIdeaException(author, clazz);
        }
        this.author = author;
        this.clazz = clazz;
    }

    @Override
    public String toString() {
        return clazz.getSimpleName();
    }
}
