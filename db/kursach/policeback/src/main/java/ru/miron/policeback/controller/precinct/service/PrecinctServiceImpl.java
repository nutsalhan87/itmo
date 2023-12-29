package ru.miron.policeback.controller.precinct.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.miron.policeback.controller.policeman.service.PolicemanService;
import ru.miron.policeback.controller.precinct.model.response.BasePrecinctResponse;
import ru.miron.policeback.controller.precinct.model.response.DistrictResponse;
import ru.miron.policeback.repositories.PrecinctRepository;

import java.util.HashSet;

@Service
@AllArgsConstructor
public class PrecinctServiceImpl implements PrecinctService {

    private final PrecinctRepository precinctRepository;

    @Override
    public BasePrecinctResponse getMyPrecinctWithDistricts(String series) {
        var projections = precinctRepository.getMyPrecinctWithDistricts(series);
        if (projections.isEmpty()) {
            return new BasePrecinctResponse(null, new HashSet<>());
        }
        var precinctNumber = projections.get(0).getPrecinctNumber();
        var districts = new HashSet<DistrictResponse>();
        for (var projection : projections) {
            districts.add(
                    new DistrictResponse(
                            projection.getDistrictId(),
                            projection.getDistrictName()
                    )
            );
        }
        return new BasePrecinctResponse(precinctNumber, districts);
    }
}
