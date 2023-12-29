package ru.miron.policeback.controller.personrel.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.miron.policeback.projections.personrel.BaseCasePersonProjection;
import ru.miron.policeback.util.EnumConverters;

@Data
@AllArgsConstructor
public class BaseCasePersonResponse {
    private Integer id;
    private String name;
    private String race;
    private String relation;
    private String note;

    public static BaseCasePersonResponse init(BaseCasePersonProjection projection) {
        return new BaseCasePersonResponse(
                projection.getId(),
                projection.getName(),
                EnumConverters.toString(projection.getRace()),
                EnumConverters.toString(projection.getRelation()),
                projection.getNote()
        );
    }
}
