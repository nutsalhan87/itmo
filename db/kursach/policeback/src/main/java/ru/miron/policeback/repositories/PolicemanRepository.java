package ru.miron.policeback.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.miron.policeback.entities.Policeman;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.miron.policeback.projections.policeman.BasePolicemanProjection;
import ru.miron.policeback.projections.policeman.CaseSlavesQueryProjection;
import ru.miron.policeback.projections.policeman.NoContextNoRankPolicemanProjection;

import java.util.List;


public interface PolicemanRepository extends JpaRepository<Policeman, Integer> {
    @Query("""
            SELECT
                per.name AS name,
                pol.rank AS rank,
                pol.dateOfIssue AS dateOfIssue
            FROM Policeman pol
            JOIN Person per ON pol.asPerson = per
            WHERE
            series = :series
            """)
    BasePolicemanProjection getSelfBaseInfo(@Param("series") String series);

    @Query("""
            SELECT
                pol_in_pr.id AS id,
                pol_in_pr.asPerson.name AS name,
                pol_in_pr.series AS series,
                pol_in_pr.dateOfIssue as dateOfIssue
            FROM Precinct pr_my
            JOIN pr_my.policemenInside pol_in_pr
            WHERE
            pr_my = (SELECT precinct FROM Policeman pol_me WHERE series = :series)
            AND
            pol_in_pr.rank != 'MAJOR'
            """)
    List<NoContextNoRankPolicemanProjection> getSlavesOf(@Param("series") String series);

    // input - crimeId, series (remove from case)
    // crime -(left join) -> crimeCase
    // crime -(left join) -> policemanCase -(left join)-> Policeman (minor)
    @Query("""
            SELECT
                (cr_c.crimeId IS NOT NULL) as caseExists,
                sl.id as id,
                sl.asPerson.name as name,
                sl.series as series,
                sl.dateOfIssue as dateOfIssue,
                p_c.status as onCaseStatus
            FROM Crime cr
            LEFT JOIN cr.crimeCase cr_c
            LEFT JOIN cr.policemenCaseAssignments p_c
            LEFT JOIN p_c.minor sl
            WHERE
            cr.id = :crimeId
            """)
    List<CaseSlavesQueryProjection> caseSlavesQuery(@Param("crimeId") Integer crimeId);

    @Query("""
            SELECT
                dis.id
            FROM Policeman pol
            JOIN pol.precinct pr
            JOIN pr.districts dis
            WHERE
            pol.series = :series
            """)
    List<Integer> getDistrictIds(@Param("series") String series);
}
