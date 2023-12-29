package ru.miron.policeback.controller.policemancase.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.miron.policeback.controller.note.service.NoteService;
import ru.miron.policeback.entities.CrimeCase;
import ru.miron.policeback.entities.PolicemanCase;
import ru.miron.policeback.repositories.PolicemanCaseRepository;
import ru.miron.policeback.util.EnumConverters;

@Service
@AllArgsConstructor
public class PolicemanCaseServiceImpl implements PolicemanCaseService {

    private final PolicemanCaseRepository policemanCaseRepository;

    @Override
    public StateBeforePut getStateBeforePut(Integer crimeId, Integer slaveId, String ownerSeries) {
        var infoBeforePut = policemanCaseRepository.getInfoBeforePut(crimeId, slaveId);
        if (infoBeforePut.isEmpty()) {
            return StateBeforePut.NO_CRIME;
        }
        if (!infoBeforePut.get().getCrimeCaseExists()) {
            return StateBeforePut.NO_CASE;
        }
        if (!infoBeforePut.get().getSlaveExists()) {
            return StateBeforePut.NO_SLAVE;
        }
        if (infoBeforePut.get().getCaseState() != CrimeCase.State.ON_WORK) {
            return StateBeforePut.CASE_NOT_ON_WORK;
        }
        if (infoBeforePut.get().getPolicemanCaseExists()) {
            if (!ownerSeries.equals(infoBeforePut.get().getOwnerSeries())) {
                return StateBeforePut.DOES_NOT_OWN;
            }
            return StateBeforePut.TO_UPDATE;
        }
        return StateBeforePut.TO_ADD;
    }

    @Override
    public int addOnCase(Integer crimeId, Integer slaveId, PolicemanCase.Status onCaseStatus, String ownerSeries) {
        return policemanCaseRepository.addOnCase(crimeId, slaveId, onCaseStatus.name(), ownerSeries);
    }

    @Override
    public int update(Integer crimeId, Integer slaveId, PolicemanCase.Status onCaseStatus) {
        return policemanCaseRepository.update(crimeId, slaveId, onCaseStatus.name());
    }
}
