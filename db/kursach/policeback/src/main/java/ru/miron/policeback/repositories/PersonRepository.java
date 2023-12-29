package ru.miron.policeback.repositories;

import org.springframework.data.jpa.repository.Query;
import ru.miron.policeback.controller.person.model.response.PersonResponse;
import ru.miron.policeback.entities.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.miron.policeback.projections.person.PersonProjection;

import java.util.List;


public interface PersonRepository extends JpaRepository<Person, Integer> {
    @Query("""
            select
                id as id,
                name as name,
                birthdate as birthdate,
                race as race
            from Person
            where
            id != (select asPerson.id from Policeman where series = :series)
            """)
    List<PersonProjection> getAllExceptCaller(String series);
}
