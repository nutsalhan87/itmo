package ru.miron.policeback.repositories;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import ru.miron.policeback.controller.note.model.response.CrimeNoteResponse;
import ru.miron.policeback.entities.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.miron.policeback.projections.crimecase.InfoBeforeCaseStateUpdateProjection;
import ru.miron.policeback.projections.note.CrimeNoteProjection;
import ru.miron.policeback.projections.note.InfoBeforeNoteCreationProjection;
import ru.miron.policeback.projections.note.InfoBeforeNoteReceivingProjection;

import java.util.List;
import java.util.Optional;


public interface NoteRepository extends JpaRepository<Note, Integer> {
    // crime -(left join)-> case
    // no crime, no case, state on case
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
    Optional<InfoBeforeNoteCreationProjection> getInfoBeforeCreation(String series, Integer crimeId);

    @Modifying
    @Transactional
    @Query(value = """
            INSERT INTO note
                (fk_crime,
                fk_policeman,
                note)
            VALUES
                (:crimeId,
                (SELECT id FROM policeman WHERE series = :series),
                :text)
            """,
            nativeQuery = true)
    int create(Integer crimeId, String series, String text);

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
    Optional<InfoBeforeNoteReceivingProjection> getInfoBeforeReceivingByMinor(String series, Integer crimeId);

    @Query("""
            SELECT
                id as id,
                policemanOwner.id as policemanOwnerId,
                note as noteText
            FROM Note n
            WHERE
            crime.id = :crimeId
            """)
    List<CrimeNoteProjection> getCaseNotes(Integer crimeId);
}
