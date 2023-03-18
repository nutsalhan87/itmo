package myMoves;

import ru.ifmo.se.pokemon.Pokemon;
import ru.ifmo.se.pokemon.SpecialMove;
import ru.ifmo.se.pokemon.Stat;
import ru.ifmo.se.pokemon.Type;

public class Psychic extends SpecialMove {
    private boolean flag;

    public Psychic() {
        super(Type.PSYCHIC, 90D, 1D);
        this.flag = false;
    }

    @Override
    protected void applyOppEffects(Pokemon p) {
        if (0.1D > Math.random()) {
            p.setMod(Stat.SPECIAL_DEFENSE, -1);
            flag = true;
        }
    }

    @Override
    public String describe() {
        if (flag) {
            return "damage and reduce spec defence level";
        } else {
            return "damage";
        }
    }
}
