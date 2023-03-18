package myMoves;

import ru.ifmo.se.pokemon.Effect;
import ru.ifmo.se.pokemon.PhysicalMove;
import ru.ifmo.se.pokemon.Pokemon;
import ru.ifmo.se.pokemon.Type;

public class BodySlam extends PhysicalMove {
    private boolean flag;

    public BodySlam() {
        super(Type.NORMAL, 85D, 1D);
        this.flag = false;
    }

    @Override
    protected void applyOppEffects(Pokemon p) {
        if (0.3 > Math.random()) {
            Effect.paralyze(p);
            flag = true;
        }
    }

    @Override
    public String describe() {
        if (flag) {
            return "damage and paralyze";
        } else {
            return "damage";
        }
    }
}
