package ru.miron.policeback.projections.policeman;

import ru.miron.policeback.entities.Policeman;

import java.time.LocalDate;

public interface BasePolicemanProjection {
    String getName();
    Policeman.Rank getRank();
    LocalDate getDateOfIssue();
}
