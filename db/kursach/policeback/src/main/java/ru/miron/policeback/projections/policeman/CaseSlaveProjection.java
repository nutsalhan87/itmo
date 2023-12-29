package ru.miron.policeback.projections.policeman;

import ru.miron.policeback.entities.PolicemanCase;

import java.time.LocalDate;

public interface CaseSlaveProjection {
    Integer getId();
    String getName();
    String getSeries();
    LocalDate getDateOfIssue();
    PolicemanCase.Status getOnCaseStatus();
}
