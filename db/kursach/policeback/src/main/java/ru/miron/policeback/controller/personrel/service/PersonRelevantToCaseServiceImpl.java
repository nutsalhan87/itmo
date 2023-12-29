package ru.miron.policeback.controller.personrel.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.miron.policeback.controller.personrel.model.response.BaseCasePersonResponse;
import ru.miron.policeback.entities.CrimeCase;
import ru.miron.policeback.entities.PersonRelevantToCase;
import ru.miron.policeback.entities.PolicemanCase;
import ru.miron.policeback.repositories.PersonRelevantToCaseRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PersonRelevantToCaseServiceImpl implements PersonRelevantToCaseService {

    private final PersonRelevantToCaseRepository personRelevantToCaseRepository;

    @Override
    public StateBeforePut getStateBeforePut(Integer crimeId, Integer personId, String series) {
        var infoBeforePut = personRelevantToCaseRepository.getInfoBeforePut(crimeId, personId, series);
        if (infoBeforePut.isEmpty()) {
            return StateBeforePut.NO_CRIME;
        }
        if (!infoBeforePut.get().getCrimeCaseExists()) {
            return StateBeforePut.NO_CASE;
        }
        if (infoBeforePut.get().getCaseState() != CrimeCase.State.ON_WORK) {
            return StateBeforePut.CASE_NOT_ON_WORK;
        }
        System.out.printf("%d, %d, %s\n", crimeId, personId, series);
        System.out.println(infoBeforePut.get().getPolicemanCaseExists());
        if (!infoBeforePut.get().getPolicemanCaseExists()) {
            return StateBeforePut.NO_POLICEMAN_CASE;
        }
        if (infoBeforePut.get().getHasRecord()) {
            if (!infoBeforePut.get().getIsOwner()) {
                return StateBeforePut.NOT_OWNING_IF_UPDATE;
            }
            return StateBeforePut.TO_UPDATE;
        }
        if (infoBeforePut.get().getIsSelfRecording()) {
            return StateBeforePut.ABOUT_SELF;
        }
        return StateBeforePut.TO_ADD;
    }

    @Override
    public int addPersonToCase(Integer crimeId, Integer personId, PersonRelevantToCase.Relation relation, String note, String series) {
        return personRelevantToCaseRepository.addCaseRelation(crimeId, personId, relation.name(), note, series);
    }

    @Override
    public int update(Integer crimeId, Integer personId, PersonRelevantToCase.Relation relation, String note) {
        return personRelevantToCaseRepository.updateCaseRelation(crimeId, personId, relation.name(), note);
    }

    @Override
    public StateBeforeReceiving getStateBeforeReceiving(Integer crimeId, String series) {
        var infoBeforeCreation = personRelevantToCaseRepository.getInfoBeforeReceiving(crimeId, series);
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
    public List<BaseCasePersonResponse> getRelatedPeople(Integer crimeId) {
        return personRelevantToCaseRepository.getRelatedPeople(crimeId).stream()
                .map(BaseCasePersonResponse::init)
                .collect(Collectors.toList());
    }
}
