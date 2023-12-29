package ru.miron.policeback.projections.person;

import ru.miron.policeback.entities.Person;

import java.time.LocalDate;

public interface PersonProjection {
    Integer getId();
    String getName();
    LocalDate getBirthdate();
    Person.Race getRace();
}
