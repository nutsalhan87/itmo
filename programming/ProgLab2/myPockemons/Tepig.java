package myPockemons;

import myMoves.LeechLife;
import myMoves.Snarl;
import myMoves.Swagger;
import myMoves.Waterfall;
import ru.ifmo.se.pokemon.Pokemon;
import ru.ifmo.se.pokemon.Type;

public class Tepig extends Pokemon {
    public Tepig(String name, int lvl) {
        super(name, lvl);
        setStats(65, 63, 45, 45, 45, 45);
        setType(Type.FIRE);
        setMove(new LeechLife(), new Waterfall(), new Swagger(), new Snarl());
    }
}
