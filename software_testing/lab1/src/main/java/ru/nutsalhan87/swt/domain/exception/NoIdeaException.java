package ru.nutsalhan87.swt.domain.exception;

import ru.nutsalhan87.swt.domain.Human;

public class NoIdeaException extends RuntimeException {
    public NoIdeaException(Human author, Class<?> clazz) {
        super(author.getName() + " don't have any ideas about " + clazz.getSimpleName() + "(-s)");
    }
}
