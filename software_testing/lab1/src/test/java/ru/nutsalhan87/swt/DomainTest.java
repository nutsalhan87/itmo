package ru.nutsalhan87.swt;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import ru.nutsalhan87.swt.domain.*;
import ru.nutsalhan87.swt.domain.exception.FullEnergyException;
import ru.nutsalhan87.swt.domain.exception.NoIdeaException;
import ru.nutsalhan87.swt.domain.exception.NoSpaceSuitException;
import ru.nutsalhan87.swt.domain.exception.TiredException;

public class DomainTest {
    @Test
    void scenario() {
        //тестируем основной сценарий предметной области
        //создаем Форда и компьютер
        var ford = new Human("Ford", 33, Location.CREW_QUARTERS);
        var computer = new Computer(Computer.MAC.random());

        //проверяем, что Форд не может уснуть
        Assertions.assertThrows(FullEnergyException.class, ford::sleep);

        //проверяем, что Форд не может написать статью на слишком общую тему
        Assertions.assertThrows(NoIdeaException.class, () -> computer.writeArticle(ford, Vogon.class));

        //надеваем на Форда халат, проверяем, что он наделся
        ford.wear(Human.Clothes.BATHROBE);
        Assertions.assertEquals(Human.Clothes.BATHROBE, ford.getClothes());

        //проверяем, что Форд спокойно поднимается на мостик без выбрасывания исключений
        Assertions.assertAll(() -> ford.move(Location.BRIDGE));
    }

    @Test
    void mac() {
        //тестируем правильность создания MAC-адреса для компьютера
        Assertions.assertEquals("2A-00-F3-16-CD-01", Computer.MAC.fromLong(0x2a00f316cd01L).toString());
    }

    @ParameterizedTest
    @ValueSource(classes = {Human.class, Location.class, Magazine.class, Computer.class, Article.class})
    void article(Class c){
        //проверяем возможность написания статей по необщим темам
        var ford = new Human("Ford", 33, Location.CREW_QUARTERS);
        var computer = new Computer(Computer.MAC.random());
        Assertions.assertEquals(c.getSimpleName(), computer.writeArticle(ford, c).toString());

        var stanford = new Human("Stanford", 33, Location.CREW_QUARTERS);
        Assertions.assertEquals(stanford, computer.writeArticle(stanford, c).getAuthor());
    }

    @Test
    void spacesuit(){
        //проверяем, что за пределы корабля без скафандра не выйти
        var ford = new Human("Ford", 33, Location.CREW_QUARTERS);
        Assertions.assertThrows(NoSpaceSuitException.class, () -> ford.move(Location.EXTERIOR));

        //надеваем скафандр и снова проверяем
        ford.wear(Human.Clothes.SPACE_SUIT);
        Assertions.assertAll(()->ford.move(Location.EXTERIOR));
    }

    @Test
    void energy(){
        var ford = new Human("Ford", 33, Location.CREW_QUARTERS);
        var computer = new Computer(Computer.MAC.random());
        Assertions.assertThrows(FullEnergyException.class, ford::sleep);
        Assertions.assertEquals(ford.getEnergy(), IntelligentCreature.Energy.FULL);

        computer.writeArticle(ford, Human.class);
        Assertions.assertEquals(ford.getEnergy(), IntelligentCreature.Energy.LOW);

        Assertions.assertThrows(TiredException.class, ()->computer.writeArticle(ford, Magazine.class));

        Assertions.assertAll(ford::sleep);
        Assertions.assertEquals(ford.getEnergy(), IntelligentCreature.Energy.FULL);
    }

    @Test
    void human_creation(){
        Assertions.assertThrows(IllegalArgumentException.class, () -> new Human("Toddler", -1, Location.BRIDGE));

        var ford = new Human("Ford", 33, Location.EXTERIOR);
        Assertions.assertEquals(Human.Clothes.SPACE_SUIT, ford.getClothes());
    }
}
