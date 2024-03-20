package ru.nutsalhan87.swt.domain;

import lombok.Getter;
import lombok.Setter;
import ru.nutsalhan87.swt.domain.exception.NoSpaceSuitException;

@Getter
public class Human extends IntelligentCreature {
    int age;
    @Setter()
    Clothes clothes;
    Location location;

    public Human(String name, int age, Location location) throws IllegalArgumentException {
        super(name);
        if (age < 0) {
            throw new IllegalArgumentException();
        }
        this.age = age;
        this.clothes = location == Location.EXTERIOR ? Clothes.SPACE_SUIT : Clothes.PANTS;
        this.location = location;
    }

    public void move(Location location) throws NoSpaceSuitException {
        if (location == Location.EXTERIOR && this.clothes != Clothes.SPACE_SUIT) {
            throw new NoSpaceSuitException(this);
        }
        this.location = location;
    }

    public void wear(Clothes clothes) {
        this.clothes = clothes;
    }

    public enum Clothes {
        BATHROBE,
        PANTS,
        SPACE_SUIT
    }
}
