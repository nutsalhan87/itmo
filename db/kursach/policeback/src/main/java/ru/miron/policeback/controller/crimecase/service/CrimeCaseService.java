package ru.miron.policeback.controller.crimecase.service;

import ru.miron.policeback.controller.crimecase.model.response.BaseCrimeCaseNoStateResponse;
import ru.miron.policeback.controller.crimecase.model.response.CasesWithMinPolicemenAndUniqueFullResponse;
import ru.miron.policeback.entities.CrimeCase;

import java.util.List;

public interface CrimeCaseService {
    StateBeforeCreation getStateBeforeCreation(Integer crimeId, String series);

    int create(Integer crimeId, String series);

    StateBeforeUpdate getStateBeforeUpdate(Integer crimeId, String series);

    int updateState(Integer crimeId, CrimeCase.State state);

    CasesWithMinPolicemenAndUniqueFullResponse getCasesWithMinPolicemanAndUniqueFull(String series);

    List<BaseCrimeCaseNoStateResponse> getOpenedCasesOnWhichMinorStatusAssigned(String series);

    enum StateBeforeUpdate {
        OK,
        CANT_CHANGE_CLOSED,
        NO_CRIME,
        NO_CASE,
        ANOTHER_OWNER
    }

    enum StateBeforeCreation {
        OK,
        CASE_EXISTS,
        WRONG_DISTRICT,
        NO_CRIME
    }
}
