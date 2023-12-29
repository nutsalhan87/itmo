package ru.miron.policeback.controller.policeman.model.response;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import ru.miron.policeback.projections.policeman.NoContextNoRankPolicemanProjection;
import ru.miron.policeback.projections.policeman.SlaveProjection;

import java.time.LocalDate;

@AllArgsConstructor
@Getter
@EqualsAndHashCode(of = "id")
public class SlaveResponse {
    private Integer id;
    private String name;
    private String series;
    private Boolean owns;
    private LocalDate seriesDateOfIssue;

    public static SlaveResponse init(SlaveProjection projection) {
        return new SlaveResponse(
                projection.getId(),
                projection.getName(),
                projection.getSeries(),
                projection.getOwnsSlave(),
                projection.getDateOfIssue()
        );
    }
}
