package ru.miron.policeback.controller.precinct;


import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.miron.policeback.controller.precinct.model.response.BasePrecinctResponse;
import ru.miron.policeback.controller.precinct.service.PrecinctService;
import ru.miron.policeback.entities.Auth;

import java.util.List;

import static ru.miron.policeback.controller.policeman.PolicemanController.getSeries;

@RestController
@RequestMapping("/api/v${api.version}")
@AllArgsConstructor
public class PrecinctController {

    private final PrecinctService precinctService;

    @GetMapping("/auth/precinct/my")
    public BasePrecinctResponse getMyPrecinctWithDistricts(Authentication authentication) {
        var series = getSeries(authentication);
        return precinctService.getMyPrecinctWithDistricts(series);
    }
}
