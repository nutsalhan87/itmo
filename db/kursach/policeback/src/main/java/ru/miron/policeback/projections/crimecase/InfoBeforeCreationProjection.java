package ru.miron.policeback.projections.crimecase;

public interface InfoBeforeCreationProjection {
    Boolean getCrimeCaseExists();
    Integer getDistrictId();
}
