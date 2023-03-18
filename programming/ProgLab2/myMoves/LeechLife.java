package myMoves;

import ru.ifmo.se.pokemon.*;

public class LeechLife extends PhysicalMove {
    public LeechLife() {
        super(Type.BUG, 20D, 1D);
    }

    @Override
    protected void applySelfDamage(Pokemon p, double damageValue) {
        p.setMod(Stat.HP, (int) (damageValue * 0.5D));
    }

    @Override
    public String describe() {
        return "increase self hp and damage";
    }
}
