package ru.nutsalhan87.swt.domain.exception;

import ru.nutsalhan87.swt.domain.IntelligentCreature;

public class TiredException extends RuntimeException {
    public TiredException(IntelligentCreature intelligentCreature) {
        super(intelligentCreature.getName() + " is tired");
    }
}
