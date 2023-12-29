package ru.miron.policeback.projections.policemancase;

import ru.miron.policeback.entities.CrimeCase;
import ru.miron.policeback.entities.PolicemanCase;

public interface InfoBeforePolicemanCasePutProjection {
    Boolean getCrimeCaseExists();
    CrimeCase.State getCaseState();
    Boolean getPolicemanCaseExists();
    String getOwnerSeries();
    Boolean getSlaveExists();
}
