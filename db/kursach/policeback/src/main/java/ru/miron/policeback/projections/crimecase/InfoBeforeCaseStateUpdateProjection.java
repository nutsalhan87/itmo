package ru.miron.policeback.projections.crimecase;

import ru.miron.policeback.entities.CrimeCase;

public interface InfoBeforeCaseStateUpdateProjection {
    Boolean getCrimeCaseExists();
    CrimeCase.State getCaseState();
    String getOwnerSeries();
}
