package myPockemons;

import myMoves.BodySlam;
import myMoves.Confide;
import myMoves.Rest;
import ru.ifmo.se.pokemon.Pokemon;
import ru.ifmo.se.pokemon.Type;

public class Sandshrew extends Pokemon {
    public Sandshrew(String name, int lvl) {
        super(name, lvl);
        setStats(50, 75, 90, 10, 35, 40);
        setType(Type.GROUND);
        setMove(new Rest(), new Confide(), new BodySlam());
    }
}
