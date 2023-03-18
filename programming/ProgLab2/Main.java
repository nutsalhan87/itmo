import myPockemons.*;
import ru.ifmo.se.pokemon.*;

public class Main {
    public static void main(String[] args){
        Battle b = new Battle();

        Golem g = new Golem("Мурад", 1);
        Magnemite m = new Magnemite("Мага", 1);
        Pidgeotto p = new Pidgeotto("Ахмед", 1);
        Sandshrew s = new Sandshrew("Гаджи", 1);
        Tepig t = new Tepig("Расул", 1);
        Wynaut w = new Wynaut("Шамиль", 1);

        b.addAlly(g);
        b.addAlly(t);
        b.addAlly(p);
        b.addFoe(w);
        b.addFoe(m);
        b.addFoe(s);

        b.go();
    }
}
