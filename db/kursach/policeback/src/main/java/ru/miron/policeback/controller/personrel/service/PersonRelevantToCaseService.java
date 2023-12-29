package ru.miron.policeback.controller.personrel.service;

import ru.miron.policeback.controller.personrel.model.response.BaseCasePersonResponse;
import ru.miron.policeback.entities.PersonRelevantToCase;

import java.util.List;

public interface PersonRelevantToCaseService {

    StateBeforePut getStateBeforePut(Integer crimeId, Integer personId, String series);

    int addPersonToCase(Integer crimeId, Integer personId, PersonRelevantToCase.Relation relation, String note, String series);

    int update(Integer crimeId, Integer personId, PersonRelevantToCase.Relation relation, String note);

    StateBeforeReceiving getStateBeforeReceiving(Integer crimeId, String series);

    List<BaseCasePersonResponse> getRelatedPeople(Integer crimeId);

    enum StateBeforeReceiving {
        OK,
        NO_POLICEMAN_CASE,
        NOT_ASSIGNED_STATE,
        NO_CRIME,
        NO_CASE
    }

    enum StateBeforePut {
        TO_ADD,
        TO_UPDATE,
        CASE_NOT_ON_WORK,
        ABOUT_SELF,
        NO_POLICEMAN_CASE,
        NOT_ASSIGNED_STATE,
        NOT_OWNING_IF_UPDATE,
        NO_CRIME,
        NO_CASE
    }
}
