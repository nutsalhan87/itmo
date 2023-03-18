package myPockemons;

import myMoves.Confide;
import myMoves.Rest;
import ru.ifmo.se.pokemon.Pokemon;
import ru.ifmo.se.pokemon.Type;

public class Magnemite extends Pokemon {
    public Magnemite(String name, int lvl) {
        super(name, lvl);
        setStats(25, 35, 70, 95, 55, 45);
        setType(Type.FIRE);
        setMove(new Rest(), new Confide());
    }
}
