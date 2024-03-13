package ru.nutsalhan87.swt.domain;

import lombok.Getter;

@Getter
public class Human extends IntelligentCreature {
    int age;
    public Human(String name, int age) throws IllegalArgumentException {
        super(name);
        if (age < 0) {
            throw new IllegalArgumentException();
        }
        this.age = age;
    }
}
