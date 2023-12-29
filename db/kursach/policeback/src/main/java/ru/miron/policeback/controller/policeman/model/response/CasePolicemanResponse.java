package ru.miron.policeback.controller.policeman.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.miron.policeback.projections.policeman.CaseSlavesQueryProjection;
import ru.miron.policeback.util.EnumConverters;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class CasePolicemanResponse {
    private Integer id;
    private String name;
    private String series;
    private LocalDate seriesDateOfIssue;
    private String onCaseStatus;

    public static CasePolicemanResponse init(CaseSlavesQueryProjection projection) {
        return new CasePolicemanResponse(
                projection.getId(),
                projection.getName(),
                projection.getSeries(),
                projection.getDateOfIssue(),
                EnumConverters.toString(projection.getOnCaseStatus())
        );
    }
}
