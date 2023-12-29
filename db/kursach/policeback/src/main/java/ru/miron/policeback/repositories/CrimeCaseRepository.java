package ru.miron.policeback.repositories;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.miron.policeback.entities.CrimeCase;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.miron.policeback.projections.crimecase.BaseCrimeCaseNoStateProjection;
import ru.miron.policeback.projections.crimecase.CaseWithSlaveRecordProjection;
import ru.miron.policeback.projections.crimecase.InfoBeforeCreationProjection;
import ru.miron.policeback.projections.crimecase.InfoBeforeCaseStateUpdateProjection;

import java.util.List;
import java.util.Optional;


public interface CrimeCaseRepository extends JpaRepository<CrimeCase, Integer> {
    @Modifying
    @Query("""
            UPDATE CrimeCase c_c
            SET c_c.state = :state
            WHERE c_c.crimeId = :crimeId
            """)
    int updateStateByCrimeId(@Param("state") CrimeCase.State state, @Param("crimeId") int crimeId);

    @Query("""
            SELECT
                (cr_c.crimeId IS NOT NULL) as crimeCaseExists,
                cr.district.id as districtId
            FROM Crime cr
            LEFT JOIN cr.crimeCase cr_c
            WHERE
            cr.id = :crimeId
            """)
    Optional<InfoBeforeCreationProjection> getInfoBeforeCreation(@Param("crimeId") Integer crimeId);

    @Modifying
    @Transactional
    @Query(value = """
            INSERT INTO crime_case
                (fk_crime,
                fk_policeman_major,
                state)
            VALUES
                (:crimeId,
                (SELECT id FROM policeman WHERE series = :series),
                'ON_WORK')
            """,
            nativeQuery = true)
    int create(@Param("crimeId") Integer crimeId, @Param("series") String series);

    @Modifying
    @Transactional
    @Query(value = """
            UPDATE crime_case
            SET
                state = :state
            WHERE
            fk_crime = :crimeId
            """,
            nativeQuery = true)
    int updateState(@Param("crimeId") Integer crimeId, @Param("state") String state);

    // crime -(left join)-> case
    // no crime, no case, cant change closed (case.state), another owner (case.policemanMajor.series)
    @Query("""
            SELECT
                (cr_c.crimeId IS NOT NULL) as crimeCaseExists,
                cr_c.state as caseState,
                cr_c.policemanMajor.series as ownerSeries
            FROM Crime cr
            LEFT JOIN cr.crimeCase cr_c
            WHERE
            cr.id = :crimeId
            """)
    Optional<InfoBeforeCaseStateUpdateProjection> getInfoBeforeStateUpdate(Integer crimeId);

    @Query("""
            SELECT
                cr.id as crimeId,
                (SELECT t.name FROM CrimeType t WHERE cr.type.id = t.id) as caseTypeName,
                (SELECT dis.name FROM District dis WHERE cr.district.id = dis.id) as districtName,
                cr_c.state as caseState,
                (cr_c.policemanMajor.id = major.id) as ownsCase,
                (pol_c.major.id = major.id) as ownsSlave,
                sl.id as slaveId,
                (SELECT name FROM Person per WHERE sl.asPerson.id = per.id) as name,
                sl.series as series,
                sl.dateOfIssue as dateOfIssue,
                pol_c.status as onCaseStatus
            FROM Crime cr
            LEFT JOIN cr.crimeCase cr_c
            LEFT JOIN cr.policemenCaseAssignments pol_c
            LEFT JOIN pol_c.minor sl
            LEFT JOIN Policeman major ON major.series = :series
            WHERE
            pol_c.major.id = major.id
            OR
            cr_c.policemanMajor.id = major.id
            """)
    List<CaseWithSlaveRecordProjection> getCasesWithPolicemenRecords(@Param("series") String series);

    @Query("""
            SELECT
                cr.id as crimeId,
                (SELECT t.name FROM CrimeType t WHERE cr.type.id = t.id) as typeName,
                (SELECT dis.name FROM District dis WHERE cr.district.id = dis.id) as districtName
            FROM Crime cr
            LEFT JOIN cr.crimeCase cr_c
            LEFT JOIN cr.policemenCaseAssignments pol_c
            LEFT JOIN pol_c.minor sl
            WHERE
            cr_c.state = 'ON_WORK'
            AND
            sl.series = :series
            AND
            pol_c.status = 'ASSIGNED'
            """)
    List<BaseCrimeCaseNoStateProjection> getOpenedCasesOnWhichMinorStatusAssigned(@Param("series") String series);
}
