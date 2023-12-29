package ru.miron.policeback.projections.personrel;

import ru.miron.policeback.entities.CrimeCase;
import ru.miron.policeback.entities.PolicemanCase;

public interface InfoBeforePersonRelReceivingProjection {
    Boolean getCrimeCaseExists();
    CrimeCase.State getCaseState();
    Boolean getPolicemanCaseExists();
    PolicemanCase.Status getOnCaseStatus();
}
