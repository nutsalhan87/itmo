package ru.miron.policeback.util;

import ru.miron.policeback.entities.PolicemanCase;

public class JpaToControllerReturnsConvertor {
    public static String convertCaseStatus(PolicemanCase.Status status) {
        return status.name().toLowerCase();
    }
}
