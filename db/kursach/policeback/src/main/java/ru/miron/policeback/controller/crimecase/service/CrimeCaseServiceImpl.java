package ru.miron.policeback.controller.crimecase.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.miron.policeback.controller.crimecase.model.response.BaseCrimeCaseNoStateResponse;
import ru.miron.policeback.controller.crimecase.model.response.CaseWithMinPolicemenResponse;
import ru.miron.policeback.controller.crimecase.model.response.CasesWithMinPolicemenAndUniqueFullResponse;
import ru.miron.policeback.controller.policeman.model.response.MinPolicemanOnCaseResponse;
import ru.miron.policeback.controller.policeman.model.response.NoContextNoRankPolicemanResponse;
import ru.miron.policeback.controller.policeman.model.response.SlaveResponse;
import ru.miron.policeback.controller.policeman.service.PolicemanService;
import ru.miron.policeback.entities.CrimeCase;
import ru.miron.policeback.repositories.CrimeCaseRepository;
import ru.miron.policeback.util.EnumConverters;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CrimeCaseServiceImpl implements CrimeCaseService {

    private final CrimeCaseRepository crimeCaseRepository;
    private final PolicemanService policemanService;

    @Override
    public StateBeforeCreation getStateBeforeCreation(Integer crimeId, String series) {
        var caseInfoBeforeCreation = crimeCaseRepository.getInfoBeforeCreation(crimeId);
        var creatorDistrictIds = policemanService.getDistrictIds(series);
        if (caseInfoBeforeCreation.isEmpty()) {
            return StateBeforeCreation.NO_CRIME;
        }
        if (caseInfoBeforeCreation.get().getCrimeCaseExists()) {
            return StateBeforeCreation.CASE_EXISTS;
        }
        if (!creatorDistrictIds.contains(caseInfoBeforeCreation.get().getDistrictId())) {
            return StateBeforeCreation.WRONG_DISTRICT;
        }
        return StateBeforeCreation.OK;
    }

    @Override
    public int create(Integer crimeId, String series) {
        return crimeCaseRepository.create(crimeId, series);
    }

    @Override
    public StateBeforeUpdate getStateBeforeUpdate(Integer crimeId, String series) {
        var caseInfoBeforeStateUpdate = crimeCaseRepository.getInfoBeforeStateUpdate(crimeId);
        if (caseInfoBeforeStateUpdate.isEmpty()) {
            return StateBeforeUpdate.NO_CRIME;
        }
        if (!caseInfoBeforeStateUpdate.get().getCrimeCaseExists()) {
            return StateBeforeUpdate.NO_CASE;
        }
        if (!caseInfoBeforeStateUpdate.get().getOwnerSeries().equals(series)) {
            return StateBeforeUpdate.ANOTHER_OWNER;
        }
        if (caseInfoBeforeStateUpdate.get().getCaseState() == CrimeCase.State.CLOSE) {
            return StateBeforeUpdate.CANT_CHANGE_CLOSED;
        }
        return StateBeforeUpdate.OK;
    }

    @Override
    public int updateState(Integer crimeId, CrimeCase.State state) {
        return crimeCaseRepository.updateState(crimeId, state.name());
    }

    @Override
    public CasesWithMinPolicemenAndUniqueFullResponse getCasesWithMinPolicemanAndUniqueFull(String series) {
        var records = crimeCaseRepository.getCasesWithPolicemenRecords(series);
        var crimeCases = new HashSet<CaseWithMinPolicemenResponse>();
        var uniquePolicemen = new HashSet<SlaveResponse>();
        var addedCrimeIdsWithMinSlaves = new HashMap<Integer, Set<MinPolicemanOnCaseResponse>>();
        var addedPolicemanIds = new HashSet<Integer>();
        for (var r : records) {
            if (!addedCrimeIdsWithMinSlaves.containsKey(r.getCrimeId())) {
                var caseResponse = new CaseWithMinPolicemenResponse(
                        r.getCrimeId(),
                        r.getCaseTypeName(),
                        r.getDistrictName(),
                        EnumConverters.toString(r.getCaseState()),
                        r.getOwnsCase(),
                        new HashSet<>()
                );
                crimeCases.add(caseResponse);
                addedCrimeIdsWithMinSlaves.put(r.getCrimeId(), caseResponse.getSlavesOnCase());
            }
            if (r.getSlaveId() != null) {
                addedCrimeIdsWithMinSlaves.get(r.getCrimeId()).add(
                        new MinPolicemanOnCaseResponse(
                                r.getSlaveId(),
                                EnumConverters.toString(r.getOnCaseStatus())
                        )
                );
                if (!addedPolicemanIds.contains(r.getSlaveId())) {
                    addedPolicemanIds.add(r.getSlaveId());
                    uniquePolicemen.add(
                            new SlaveResponse(
                                    r.getSlaveId(),
                                    r.getName(),
                                    r.getSeries(),
                                    r.getOwnsSlave(),
                                    r.getDateOfIssue()
                            )
                    );
                }
            }
        }
        return new CasesWithMinPolicemenAndUniqueFullResponse(crimeCases, uniquePolicemen);
    }

    @Override
    public List<BaseCrimeCaseNoStateResponse> getOpenedCasesOnWhichMinorStatusAssigned(String series) {
        var projectionsList = crimeCaseRepository.getOpenedCasesOnWhichMinorStatusAssigned(series);
        return projectionsList.stream()
                .map(BaseCrimeCaseNoStateResponse::init)
                .collect(Collectors.toList());
    }

}
