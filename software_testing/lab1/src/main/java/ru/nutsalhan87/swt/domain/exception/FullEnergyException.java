package ru.nutsalhan87.swt.domain.exception;

import ru.nutsalhan87.swt.domain.IntelligentCreature;

public class FullEnergyException extends RuntimeException {
    public FullEnergyException(IntelligentCreature intelligentCreature) {
        super(intelligentCreature.getName() + " have full energy");
    }
}
