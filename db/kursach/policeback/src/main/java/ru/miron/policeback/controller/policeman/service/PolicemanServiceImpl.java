package ru.miron.policeback.controller.policeman.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.miron.policeback.controller.policeman.model.response.BasePolicemanResponse;
import ru.miron.policeback.controller.policeman.model.response.CasePolicemanResponse;
import ru.miron.policeback.controller.policeman.model.response.NoContextNoRankPolicemanResponse;
import ru.miron.policeback.entities.PolicemanCase;
import ru.miron.policeback.repositories.PolicemanRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PolicemanServiceImpl implements PolicemanService {

    private PolicemanRepository policemanRepository;

    @Override
    public BasePolicemanResponse getSelfBaseInfo(String series) {
        var projection = policemanRepository.getSelfBaseInfo(series);
        return BasePolicemanResponse.init(projection, series);
    }

    @Override
    public List<NoContextNoRankPolicemanResponse> getSlaves(String series) {
        var projection = policemanRepository.getSlavesOf(series);
        return projection.stream()
                .map(NoContextNoRankPolicemanResponse::init)
                .collect(Collectors.toList());
    }

    @Override
    public CasePartnersResponse getCasePartners(Integer crimeId, String series) {
        var projectionsList = policemanRepository.caseSlavesQuery(crimeId);
        if (projectionsList.isEmpty() || !projectionsList.get(0).getCaseExists()) {
            return CasePartnersResponse.initNotOk(CasePartnersResponse.Cases.NO_CRIME);
        }
        boolean isOnCase = false;
        for (var iterator = projectionsList.iterator(); iterator.hasNext();) {
            var current = iterator.next();
            if (current.getSeries().equals(series)) {
                if (current.getOnCaseStatus() == PolicemanCase.Status.REMOVED) {
                    return CasePartnersResponse.initNotOk(CasePartnersResponse.Cases.REMOVED);
                }
                isOnCase = true;
                iterator.remove();
                break;
            }
        }
        if (!isOnCase) {
            return CasePartnersResponse.initNotOk(CasePartnersResponse.Cases.NOT_ASSIGNED);
        }
        return CasePartnersResponse.initOk(
                projectionsList.stream()
                        .map(CasePolicemanResponse::init)
                        .collect(Collectors.toList())
        );
    }

    @Override
    public List<Integer> getDistrictIds(String series) {
        return policemanRepository.getDistrictIds(series);
    }
}
