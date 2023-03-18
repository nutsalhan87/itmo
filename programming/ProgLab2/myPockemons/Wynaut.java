package myPockemons;

import myMoves.LeechLife;
import myMoves.Swagger;
import myMoves.Waterfall;
import ru.ifmo.se.pokemon.Pokemon;
import ru.ifmo.se.pokemon.Type;

public class Wynaut extends Pokemon {
    public Wynaut(String name, int lvl) {
        super(name, lvl);
        setStats(95, 23, 48, 23, 48, 23);
        setType(Type.PSYCHIC);
        setMove(new LeechLife(), new Waterfall(), new Swagger());
    }
}