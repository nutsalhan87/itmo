package myMoves;

import ru.ifmo.se.pokemon.*;

public class Rest extends StatusMove {
    public Rest() {
        super(Type.PSYCHIC, 0D, 1D);
    }

    @Override
    protected void applySelfEffects(Pokemon p) {
        p.restore();
        p.setCondition(new Effect().condition(Status.SLEEP).turns(2));
    }

    @Override
    public String describe() {
        return "restore HP and sleep for 2 rounds";
    }
}
