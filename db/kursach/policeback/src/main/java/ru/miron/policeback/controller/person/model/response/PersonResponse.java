package ru.miron.policeback.controller.person.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.miron.policeback.projections.person.PersonProjection;
import ru.miron.policeback.util.EnumConverters;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class PersonResponse {
    private Integer id;
    private String name;
    private LocalDate birthdate;
    private String race;

    public static PersonResponse init(PersonProjection projection) {
        return new PersonResponse(
                projection.getId(),
                projection.getName(),
                projection.getBirthdate(),
                EnumConverters.toString(projection.getRace())
        );
    }
}
