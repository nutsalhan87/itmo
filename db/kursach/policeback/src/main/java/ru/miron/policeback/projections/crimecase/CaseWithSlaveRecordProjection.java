package ru.miron.policeback.projections.crimecase;

import ru.miron.policeback.entities.CrimeCase;
import ru.miron.policeback.entities.PolicemanCase;

import java.time.LocalDate;

public interface CaseWithSlaveRecordProjection {
    Integer getCrimeId();
    String getCaseTypeName();
    String getDistrictName();
    CrimeCase.State getCaseState();
    Boolean getOwnsCase();
    Boolean getOwnsSlave();
    Integer getSlaveId();
    String getName();
    String getSeries();
    LocalDate getDateOfIssue();
    PolicemanCase.Status getOnCaseStatus();
    Integer getMajorId();
}
