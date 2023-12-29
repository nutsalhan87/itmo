package ru.miron.policeback.controller.policeman;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.authentication.UserServiceBeanDefinitionParser;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.miron.policeback.controller.policeman.model.response.BasePolicemanResponse;
import ru.miron.policeback.controller.policeman.model.response.CasePolicemanResponse;
import ru.miron.policeback.controller.policeman.model.response.NoContextNoRankPolicemanResponse;
import ru.miron.policeback.controller.policeman.service.PolicemanService;

import java.util.List;

@RestController
@RequestMapping("/api/v${api.version}")
@AllArgsConstructor
public class PolicemanController {

    private final PolicemanService policemanService;

    @GetMapping("/auth/policeman/self")
    public BasePolicemanResponse getSelf(Authentication authentication) {
        var series = getSeries(authentication);
        return policemanService.getSelfBaseInfo(series);
    }

    @GetMapping("/auth/major/slaves/my")
    public List<NoContextNoRankPolicemanResponse> getMySlaves(Authentication authentication) {
        var series = getSeries(authentication);
        return policemanService.getSlaves(series);
    }

    @GetMapping("/auth/minor/policeman/partners-in-case/{crime-id}")
    public ResponseEntity<List<CasePolicemanResponse>> getCasePartners(@PathVariable("crime-id") Integer crimeId,
                                                                       Authentication authentication) {
        var series = getSeries(authentication);
        var casePartnersServiceResponse = policemanService.getCasePartners(crimeId, series);
        return switch (casePartnersServiceResponse.getResponseCase()) {
            case OK -> new ResponseEntity<>(casePartnersServiceResponse.getOkResponse(), HttpStatus.OK);
            case NOT_ASSIGNED, REMOVED -> new ResponseEntity<>(HttpStatus.FORBIDDEN);
            case NO_CRIME -> new ResponseEntity<>(HttpStatus.NOT_FOUND);
        };
    }

    public static String getSeries(Authentication authentication) {
        return authentication.getName();
    }

}
