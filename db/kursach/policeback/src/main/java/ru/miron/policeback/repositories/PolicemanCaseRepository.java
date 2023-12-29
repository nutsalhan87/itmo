package ru.miron.policeback.repositories;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.miron.policeback.entities.PolicemanCase;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.miron.policeback.projections.policemancase.InfoBeforePolicemanCasePutProjection;

import java.util.Optional;


public interface PolicemanCaseRepository extends JpaRepository<PolicemanCase, String> {
    @Query("""
            SELECT
                (cr_c.crimeId IS NOT NULL) as crimeCaseExists,
                cr_c.state as caseState,
                (pol_c.crimeId IS NOT NULL) as policemanCaseExists,
                (SELECT pol_cr.series FROM Policeman pol_cr WHERE pol_c.major.id = pol_cr.id) as ownerSeries,
                (pol.id IS NOT NULL) as slaveExists
            FROM Crime cr
            LEFT JOIN cr.crimeCase cr_c
            LEFT JOIN PolicemanCase pol_c ON cr = pol_c.crime AND pol_c.minorId = :slaveId
            LEFT JOIN Policeman pol ON pol.id = :slaveId
            WHERE
            cr.id = :crimeId
            """)
    Optional<InfoBeforePolicemanCasePutProjection> getInfoBeforePut(Integer crimeId, Integer slaveId);

    @Modifying
    @Transactional
    @Query(value = """
            INSERT INTO policeman_case
                (fk_crime,
                fk_policeman_minor,
                status,
                fk_policeman_major)
            VALUES
                (:crimeId,
                :slaveId,
                :onCaseStatus,
                (SELECT id FROM policeman WHERE series = :ownerSeries))
            """,
            nativeQuery = true)
    int addOnCase(Integer crimeId, Integer slaveId, String onCaseStatus, String ownerSeries);


    @Modifying
    @Transactional
    @Query(value = """
            UPDATE policeman_case
            SET
                status = :newOnCaseStatus
            WHERE
            fk_crime = :crimeId
            AND
            fk_policeman_minor = :slaveId
            """,
            nativeQuery = true)
    int update(Integer crimeId, Integer slaveId, String newOnCaseStatus);
}
