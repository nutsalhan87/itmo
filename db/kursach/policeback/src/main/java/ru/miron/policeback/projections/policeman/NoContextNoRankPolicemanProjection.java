package ru.miron.policeback.projections.policeman;

import java.time.LocalDate;

public interface NoContextNoRankPolicemanProjection {
    Integer getId();
    String getName();
    String getSeries();
    LocalDate getDateOfIssue();
}
