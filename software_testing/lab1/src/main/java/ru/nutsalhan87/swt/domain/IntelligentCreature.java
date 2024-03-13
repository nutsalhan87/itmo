package ru.nutsalhan87.swt.domain;

import lombok.Getter;

@Getter
public abstract class IntelligentCreature {
    private final String name;
    private Energy energy;

    public IntelligentCreature(String name) {
        this.name = name;
        this.energy = Energy.FULL;
    }

    boolean tryWork() {
        return switch (this.energy) {
            case LOW -> false;
            case FULL -> {
                this.energy = Energy.LOW;
                yield true;
            }
        };
    }

    boolean trySleep() {
        return switch (this.energy) {
            case LOW -> {
                this.energy = Energy.FULL;
                yield true;
            }
            case FULL -> false;
        };
    }

    enum Energy {
        LOW,
        FULL
    }
}
