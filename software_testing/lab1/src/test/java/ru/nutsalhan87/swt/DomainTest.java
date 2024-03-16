package ru.nutsalhan87.swt;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.nutsalhan87.swt.domain.*;
import ru.nutsalhan87.swt.domain.exception.NoIdeaException;

public class DomainTest {
    @Test
    void scenario() {
        var ford = new Human("Ford", 33, Location.CREW_QUARTERS);
        var computer = new Computer(Computer.MAC.random());
        Assertions.assertThrows(NoIdeaException.class, () -> computer.writeArticle(ford, Vogon.class));
    }

    @Test
    void mac() {
        Assertions.assertEquals("2A-00-F3-16-CD-01", Computer.MAC.fromLong(0x2a00f316cd01L).toString());
    }
}
