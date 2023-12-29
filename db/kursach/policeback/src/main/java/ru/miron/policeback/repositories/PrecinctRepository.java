package ru.miron.policeback.repositories;

import org.springframework.data.jpa.repository.Query;
import ru.miron.policeback.entities.Precinct;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.miron.policeback.projections.precinct.BasePrecinctProjection;

import java.util.List;


public interface PrecinctRepository extends JpaRepository<Precinct, Integer> {
    @Query("""
            select
                pr.number as precinctNumber,
                dis.id as districtId,
                dis.name as districtName
            from Precinct pr
            join pr.districts dis
            where
            pr = (select precinct from Policeman where series = :series)
            """)
    List<BasePrecinctProjection> getMyPrecinctWithDistricts(String series);
}
