package ru.nutsalhan87.swt.domain;

import lombok.Getter;
import ru.nutsalhan87.swt.domain.exception.FullEnergyException;
import ru.nutsalhan87.swt.domain.exception.TiredException;

@Getter
public abstract class IntelligentCreature {
    private final String name;
    private Energy energy;

    public IntelligentCreature(String name) {
        this.name = name;
        this.energy = Energy.FULL;
    }

    void work() throws TiredException {
        switch (this.energy) {
            case LOW -> throw new TiredException(this);
            case FULL -> this.energy = Energy.LOW;
        }
    }

    public void sleep() throws FullEnergyException {
        switch (this.energy) {
            case LOW -> this.energy = Energy.FULL;
            case FULL -> throw new FullEnergyException(this);
        }
    }

    public enum Energy {
        LOW,
        FULL
    }
}
