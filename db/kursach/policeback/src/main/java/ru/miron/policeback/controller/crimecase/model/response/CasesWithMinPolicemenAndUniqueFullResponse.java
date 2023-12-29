package ru.miron.policeback.controller.crimecase.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.miron.policeback.controller.policeman.model.response.NoContextNoRankPolicemanResponse;
import ru.miron.policeback.controller.policeman.model.response.SlaveResponse;

import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
public class CasesWithMinPolicemenAndUniqueFullResponse {
    private Set<CaseWithMinPolicemenResponse> crimeCases;
    private Set<SlaveResponse> uniquePolicemen;
}
