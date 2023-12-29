package ru.miron.policeback.controller.precinct.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Getter
public class DistrictResponse {
    private Integer id;
    private String name;
}
