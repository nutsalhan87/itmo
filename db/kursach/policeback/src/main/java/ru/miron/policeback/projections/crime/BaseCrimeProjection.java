package ru.miron.policeback.projections.crime;

public interface BaseCrimeProjection {
    Integer getId();
    String getTypeName();
    String getDistrictName();
    Boolean getIsCaseOpened();
}
