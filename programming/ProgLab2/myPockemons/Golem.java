package myPockemons;

import myMoves.Blizzard;
import myMoves.ConfuseRay;
import myMoves.Psychic;
import ru.ifmo.se.pokemon.Pokemon;
import ru.ifmo.se.pokemon.Type;
import myMoves.DragonPulse;

public class Golem extends Pokemon {
    public Golem(String name, int lvl) {
        super(name, lvl);
        setStats(80, 120, 130, 55, 65, 45);
        setType(Type.ROCK, Type.GROUND);
        setMove(new DragonPulse(), new ConfuseRay(), new Blizzard(), new Psychic());
    }
}
