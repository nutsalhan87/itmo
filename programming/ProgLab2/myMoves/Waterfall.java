package myMoves;

import ru.ifmo.se.pokemon.Effect;
import ru.ifmo.se.pokemon.PhysicalMove;
import ru.ifmo.se.pokemon.Pokemon;
import ru.ifmo.se.pokemon.Type;

public class Waterfall extends PhysicalMove {
    private boolean flag;

    public Waterfall() {
        super(Type.WATER, 80D, 1D);
        this.flag = false;
    }

    @Override
    protected void applyOppEffects(Pokemon p) {
        if (0.2D > Math.random()) {
            Effect.flinch(p);
            flag = true;
        }
    }

    @Override
    public String describe() {
        if (flag) {
            return "damage and flinch";
        } else {
            return "damage";
        }
    }
}

