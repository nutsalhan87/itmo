package ru.miron.policeback.controller.policeman.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import ru.miron.policeback.projections.policeman.NoContextNoRankPolicemanProjection;

import java.time.LocalDate;

@AllArgsConstructor
@Getter
@EqualsAndHashCode(of = "id")
public class NoContextNoRankPolicemanResponse {
    private Integer id;
    private String name;
    private String series;
    private LocalDate seriesDateOfIssue;

    public static NoContextNoRankPolicemanResponse init(NoContextNoRankPolicemanProjection projection) {
        return new NoContextNoRankPolicemanResponse(
                projection.getId(),
                projection.getName(),
                projection.getSeries(),
                projection.getDateOfIssue()
        );
    }
}
