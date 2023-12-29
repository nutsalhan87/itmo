package ru.miron.policeback.controller.crimecase.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import ru.miron.policeback.controller.policeman.model.response.MinPolicemanOnCaseResponse;

import java.util.Set;

@AllArgsConstructor
@Getter
@EqualsAndHashCode(of = "id")
public class CaseWithMinPolicemenResponse {
    private Integer id;
    private String type;
    private String district;
    private String state;
    private Boolean owns;
    private Set<MinPolicemanOnCaseResponse> slavesOnCase;
}
