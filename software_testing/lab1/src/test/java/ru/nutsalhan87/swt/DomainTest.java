package ru.nutsalhan87.swt;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.nutsalhan87.swt.domain.*;

public class DomainTest {
    @Test
    void t() {
        var ford = new Human("Ford", 33);
        Assertions.assertThrows(NoIdeaException.class, () -> new Article<>(ford, Vogon.class));
        var article = new Article<>(ford, Human.class);
        var travelGuide = Magazine
                .builder()
                .name("Путеводитель")
                .article(article)
                .article(new Article<>(ford, Object.class))
                .build();
        System.out.println(travelGuide);
    }
}
