package ru.miron.policeback.controller.crime;

import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.miron.policeback.controller.crime.model.response.BaseCrimeResponse;
import ru.miron.policeback.controller.crime.service.CrimeService;

import java.util.List;

import static ru.miron.policeback.controller.policeman.PolicemanController.getSeries;

@RestController
@RequestMapping("/api/v${api.version}")
@AllArgsConstructor
public class CrimeController {

    private final CrimeService crimeService;

    @GetMapping("/auth/major/crime/in-my-precinct-districts/list")
    public List<BaseCrimeResponse> getMyPrecinctDistrictsCrimes(Authentication authentication) {
        var series = getSeries(authentication);
        return crimeService.getBaseByPrecinctDistricts(series);
    }
}
