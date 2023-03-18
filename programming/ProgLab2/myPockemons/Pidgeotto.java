package myPockemons;

import myMoves.BodySlam;
import myMoves.Confide;
import myMoves.Rest;
import ru.ifmo.se.pokemon.Pokemon;
import ru.ifmo.se.pokemon.Type;

public class Pidgeotto extends Pokemon {
    public Pidgeotto(String name, int lvl) {
        super(name, lvl);
        setStats(63, 60, 55, 50, 50, 71);
        setType(Type.NORMAL, Type.FLYING);
        setMove(new Rest(), new Confide(), new BodySlam());
    }
}
