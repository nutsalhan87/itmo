package myMoves;

import ru.ifmo.se.pokemon.Effect;
import ru.ifmo.se.pokemon.SpecialMove;
import ru.ifmo.se.pokemon.Type;
import ru.ifmo.se.pokemon.Pokemon;

public class Blizzard extends SpecialMove {
    private boolean flag;

    public Blizzard() {
        super(Type.ICE, 110D, 0.7D);
        this.flag = false;
    }

    protected void applyOppEffects(Pokemon p) {
        if (0.1D > Math.random()) {
            Effect.freeze(p);
            flag = true;
        }
    }

    @Override
    public String describe() {
        if (flag) {
            return "damage and freeze";
        } else {
            return "damage";
        }
    }
}