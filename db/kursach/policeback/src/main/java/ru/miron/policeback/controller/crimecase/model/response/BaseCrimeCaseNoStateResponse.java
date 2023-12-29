package ru.miron.policeback.controller.crimecase.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.miron.policeback.projections.crimecase.BaseCrimeCaseNoStateProjection;

@Data
@AllArgsConstructor
public class BaseCrimeCaseNoStateResponse {
    private Integer id;
    private String type;
    private String district;

    public static BaseCrimeCaseNoStateResponse init(BaseCrimeCaseNoStateProjection projection) {
        return new BaseCrimeCaseNoStateResponse(
            projection.getCrimeId(),
            projection.getTypeName(),
            projection.getDistrictName()
        );
    }
}
