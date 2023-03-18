package myMoves;

import ru.ifmo.se.pokemon.SpecialMove;
import ru.ifmo.se.pokemon.Type;

public class DragonPulse extends SpecialMove {
    public DragonPulse() {
        super(Type.DRAGON, 85D, 1D);
    }

    @Override
    public String describe() {
        return "damage";
    }
}
