package ru.miron.policeback.controller.precinct.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;

@Data
@AllArgsConstructor
public class BasePrecinctResponse {
    private Integer number;
    private Set<DistrictResponse> districts;
}
