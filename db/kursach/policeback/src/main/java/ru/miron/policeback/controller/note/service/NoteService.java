package ru.miron.policeback.controller.note.service;

import ru.miron.policeback.controller.crimecase.service.CrimeCaseService;
import ru.miron.policeback.controller.note.model.response.CrimeNoteResponse;

import java.util.List;

public interface NoteService {
    StateBeforeCreation getStateBeforeCreation(String series, Integer crimeId);

    int create(Integer crimeId, String series, String text);

    StateBeforeReceiving getStateBeforeOnCaseReceivingByMinor(String series, Integer crimeId);

    List<CrimeNoteResponse> getCaseNotes(Integer crimeId);

    enum StateBeforeCreation {
        OK,
        NO_POLICEMAN_CASE,
        NOT_ASSIGNED_STATE,
        NO_CRIME,
        NO_CASE
    }

    enum StateBeforeReceiving {
        OK,
        NO_POLICEMAN_CASE,
        NOT_ASSIGNED_STATE,
        NO_CRIME,
        NO_CASE
    }
}
