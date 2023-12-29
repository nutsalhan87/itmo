package ru.miron.policeback.controller.policeman.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class MinPolicemanOnCaseResponse {
    private Integer id;
    private String status;
}
