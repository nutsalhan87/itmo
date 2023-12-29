package ru.miron.policeback.controller.note.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.miron.policeback.controller.crimecase.service.CrimeCaseService;
import ru.miron.policeback.controller.note.model.response.CrimeNoteResponse;
import ru.miron.policeback.entities.CrimeCase;
import ru.miron.policeback.entities.PolicemanCase;
import ru.miron.policeback.repositories.NoteRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class NoteServiceImpl implements NoteService {

    private final NoteRepository noteRepository;

    @Override
    public StateBeforeCreation getStateBeforeCreation(String series, Integer crimeId) {
        var infoBeforeCreation = noteRepository.getInfoBeforeCreation(series, crimeId);
        if (infoBeforeCreation.isEmpty()) {
            return StateBeforeCreation.NO_CRIME;
        }
        if (!infoBeforeCreation.get().getCrimeCaseExists()) {
            return StateBeforeCreation.NO_CASE;
        }
        if (!infoBeforeCreation.get().getPolicemanCaseExists()) {
            return StateBeforeCreation.NO_POLICEMAN_CASE;
        }
        if (infoBeforeCreation.get().getOnCaseStatus() != PolicemanCase.Status.ASSIGNED) {
            return StateBeforeCreation.NOT_ASSIGNED_STATE;
        }
        return StateBeforeCreation.OK;
    }

    @Override
    public int create(Integer crimeId, String series, String text) {
        return noteRepository.create(crimeId, series, text);
    }

    @Override
    public StateBeforeReceiving getStateBeforeOnCaseReceivingByMinor(String series, Integer crimeId) {
        var infoBeforeCreation = noteRepository.getInfoBeforeReceivingByMinor(series, crimeId);
        if (infoBeforeCreation.isEmpty()) {
            return StateBeforeReceiving.NO_CRIME;
        }
        if (!infoBeforeCreation.get().getCrimeCaseExists()) {
            return StateBeforeReceiving.NO_CASE;
        }
        if (!infoBeforeCreation.get().getPolicemanCaseExists()) {
            return StateBeforeReceiving.NO_POLICEMAN_CASE;
        }
        if (infoBeforeCreation.get().getOnCaseStatus() != PolicemanCase.Status.ASSIGNED) {
            return StateBeforeReceiving.NOT_ASSIGNED_STATE;
        }
        return StateBeforeReceiving.OK;
    }

    @Override
    public List<CrimeNoteResponse> getCaseNotes(Integer crimeId) {
        return noteRepository.getCaseNotes(crimeId).stream()
                .map(CrimeNoteResponse::init)
                .collect(Collectors.toList());
    }
}
