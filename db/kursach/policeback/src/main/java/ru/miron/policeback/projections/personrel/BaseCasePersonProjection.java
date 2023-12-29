package ru.miron.policeback.projections.personrel;

import ru.miron.policeback.entities.Person;
import ru.miron.policeback.entities.PersonRelevantToCase;

public interface BaseCasePersonProjection {
    Integer getId();
    String getName();
    Person.Race getRace();
    PersonRelevantToCase.Relation getRelation();
    String getNote();
}
