package myMoves;

import ru.ifmo.se.pokemon.Pokemon;
import ru.ifmo.se.pokemon.SpecialMove;
import ru.ifmo.se.pokemon.Stat;
import ru.ifmo.se.pokemon.Type;

public class Snarl extends SpecialMove {
    public Snarl() {
        super(Type.DARK, 55D, 0.95D);
    }

    @Override
    protected void applyOppEffects(Pokemon p) {
        p.setMod(Stat.SPECIAL_ATTACK, -1);
    }

    @Override
    public String describe() {
        return "damage and decrease enemy's spec attack";
    }
}
