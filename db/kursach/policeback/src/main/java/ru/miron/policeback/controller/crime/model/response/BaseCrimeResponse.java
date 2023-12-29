package ru.miron.policeback.controller.crime.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.miron.policeback.projections.crime.BaseCrimeProjection;

@Data
@AllArgsConstructor
public class BaseCrimeResponse {
    private Integer id;
    private String type;
    private String district;
    private Boolean caseOpened;

    public static BaseCrimeResponse init(BaseCrimeProjection projection) {
        return new BaseCrimeResponse(
                projection.getId(),
                projection.getTypeName(),
                projection.getDistrictName(),
                projection.getIsCaseOpened()
        );
    }
}
