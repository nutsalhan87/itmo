package ru.nutsalhan87.swt.domain.exception;

import ru.nutsalhan87.swt.domain.Human;

public class NoSpaceSuitException extends RuntimeException {
    public NoSpaceSuitException(Human human) {
        super(human.getName() + " didn't put on a space suit");
    }
}
