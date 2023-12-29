package ru.miron.policeback.controller.policemancase.service;

import org.springframework.data.jpa.repository.Query;
import ru.miron.policeback.entities.CrimeCase;
import ru.miron.policeback.entities.PolicemanCase;

public interface PolicemanCaseService {
    StateBeforePut getStateBeforePut(Integer crimeId, Integer slaveId, String ownerSeries);

    int addOnCase(Integer crimeId, Integer slaveId, PolicemanCase.Status onCaseStatus, String ownerSeries);

    int update(Integer crimeId, Integer slaveId, PolicemanCase.Status onCaseStatus);

    enum StateBeforePut {
        TO_ADD,
        TO_UPDATE,
        CASE_NOT_ON_WORK,
        DOES_NOT_OWN,
        NO_CRIME,
        NO_CASE,
        NO_SLAVE
    }

}
