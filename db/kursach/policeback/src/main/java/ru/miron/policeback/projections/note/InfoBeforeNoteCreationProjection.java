package ru.miron.policeback.projections.note;

import ru.miron.policeback.entities.CrimeCase;
import ru.miron.policeback.entities.PolicemanCase;

public interface InfoBeforeNoteCreationProjection {
    Boolean getCrimeCaseExists();
    CrimeCase.State getCaseState();
    Boolean getPolicemanCaseExists();
    PolicemanCase.Status getOnCaseStatus();
}
