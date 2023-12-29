package ru.miron.policeback.projections.policeman;

import java.time.LocalDate;

public interface SlaveProjection {
    Integer getId();
    String getName();
    String getSeries();
    Boolean getOwnsSlave();
    LocalDate getDateOfIssue();
}
