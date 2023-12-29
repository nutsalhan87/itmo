package ru.miron.policeback.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.miron.policeback.controller.crime.model.response.BaseCrimeResponse;
import ru.miron.policeback.entities.Crime;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.miron.policeback.projections.crime.BaseCrimeProjection;

import java.util.List;

public interface CrimeRepository extends JpaRepository<Crime, Integer> {

    @Query(value = """
        SELECT
            cr.id AS id,
            cr_t.name AS typeName,
            dis.name AS districtName,
            (EXISTS (SELECT 1 FROM CrimeCase cr_c WHERE cr = cr_c.crime)) AS isCaseOpened
        FROM Policeman pol
        JOIN pol.precinct pr
        JOIN pr.districts dis
        JOIN dis.districtCrimes cr
        JOIN cr.type cr_t
        WHERE
        pol.series = :series
        """)
    List<BaseCrimeProjection> getBaseByPrecinctDistricts(@Param("series") String series);
}
