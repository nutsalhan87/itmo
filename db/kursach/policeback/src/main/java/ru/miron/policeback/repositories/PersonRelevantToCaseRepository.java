package ru.miron.policeback.repositories;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import ru.miron.policeback.controller.personrel.model.response.BaseCasePersonResponse;
import ru.miron.policeback.controller.personrel.service.PersonRelevantToCaseService;
import ru.miron.policeback.entities.PersonRelevantToCase;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.miron.policeback.projections.crimecase.InfoBeforeCaseStateUpdateProjection;
import ru.miron.policeback.projections.note.InfoBeforeNoteReceivingProjection;
import ru.miron.policeback.projections.personrel.BaseCasePersonProjection;
import ru.miron.policeback.projections.personrel.InfoBeforePersonRelReceivingProjection;
import ru.miron.policeback.projections.personrel.InfoBeforePersonToCasePutProjection;

import java.util.Collection;
import java.util.List;
import java.util.Optional;


public interface PersonRelevantToCaseRepository extends JpaRepository<PersonRelevantToCase, String> {

    @Query("""
            SELECT
                (cr_c.crimeId IS NOT NULL) as crimeCaseExists,
                cr_c.state as caseState,
                (pol_c.crimeId IS NOT NULL) as policemanCaseExists,
                pol_c.status as onCaseStatus,
                (per.id IS NOT NULL) as personExists,
                (per_c_r.personId IS NOT NULL) as hasRecord,
                (per_c_r.policeman.id = (SELECT id FROM Policeman WHERE series = :series)) as isOwner,
                (per.id = (SELECT asPerson.id FROM Policeman WHERE series = :series)) as isSelfRecording
            FROM Crime cr
            LEFT JOIN cr.crimeCase cr_c
            LEFT JOIN cr.policemenCaseAssignments pol_c ON pol_c.minor.series = :series
            LEFT JOIN cr.peopleCaseRelations per_c_r ON per_c_r.person.id = :personId
            LEFT JOIN Person per ON per.id = :personId
            WHERE
            cr.id = :crimeId
            """)
    Optional<InfoBeforePersonToCasePutProjection> getInfoBeforePut(Integer crimeId, Integer personId, String series);


    @Modifying
    @Transactional
    @Query(value = """
            INSERT INTO person_relevant_to_case
                (fk_crime,
                fk_person,
                relation,
                note,
                fk_policeman)
            VALUES
                (:crimeId,
                :personId,
                :relationName,
                :note,
                (SELECT id FROM policeman WHERE series = :series))
            """,
            nativeQuery = true)
    int addCaseRelation(Integer crimeId, Integer personId, String relationName, String note, String series);

    @Modifying
    @Transactional
    @Query(value = """
            UPDATE person_relevant_to_case
            SET
                relation = :relationName,
                note = :note
            WHERE
            fk_crime = :crimeId
            AND
            fk_person = :personId
            """,
            nativeQuery = true)
    int updateCaseRelation(Integer crimeId, Integer personId, String relationName, String note);

    @Query("""
            SELECT
                (cr_c.crimeId IS NOT NULL) as crimeCaseExists,
                cr_c.state as caseState,
                (pol_c.crimeId IS NOT NULL) as policemanCaseExists,
                pol_c.status as onCaseStatus
            FROM Crime cr
            LEFT JOIN cr.crimeCase cr_c
            LEFT JOIN cr.policemenCaseAssignments pol_c ON pol_c.minor.series = :series
            WHERE
            cr.id = :crimeId
            """)
    Optional<InfoBeforePersonRelReceivingProjection> getInfoBeforeReceiving(Integer crimeId, String series);

    @Query("""
            SELECT
                per.id as id,
                per.name as name,
                per.race as race,
                per_r_c.relation as relation,
                per_r_c.note as note
            FROM PersonRelevantToCase per_r_c
            JOIN per_r_c.person per
            WHERE
            per_r_c.crimeId = :crimeId
            """)
    List<BaseCasePersonProjection> getRelatedPeople(Integer crimeId);
}
