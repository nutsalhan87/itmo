package ru.miron.policeback.controller.policeman.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.miron.policeback.projections.policeman.BasePolicemanProjection;
import ru.miron.policeback.util.EnumConverters;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class BasePolicemanResponse {
    private String name;
    private String rank;
    private String series;
    private LocalDate seriesDateOfIssue;

    public static BasePolicemanResponse init(BasePolicemanProjection projection, String series) {
        return new BasePolicemanResponse(
                projection.getName(),
                EnumConverters.toString(projection.getRank()),
                series,
                projection.getDateOfIssue()
        );
    }
}
