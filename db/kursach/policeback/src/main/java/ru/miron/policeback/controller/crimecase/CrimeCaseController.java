package ru.miron.policeback.controller.crimecase;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import ru.miron.policeback.controller.crime.model.request.CrimeIdRequest;
import ru.miron.policeback.controller.crimecase.model.response.BaseCrimeCaseNoStateResponse;
import ru.miron.policeback.controller.crimecase.model.response.CasesWithMinPolicemenAndUniqueFullResponse;
import ru.miron.policeback.controller.crimecase.service.CrimeCaseService;
import ru.miron.policeback.entities.CrimeCase;
import ru.miron.policeback.exceptions.IllegalEnumValueException;
import ru.miron.policeback.projections.crimecase.BaseCrimeCaseNoStateProjection;
import ru.miron.policeback.util.EnumConverters;

import java.util.List;

import static ru.miron.policeback.controller.policeman.PolicemanController.getSeries;

@RestController
@RequestMapping("/api/v${api.version}")
@AllArgsConstructor
public class CrimeCaseController {

    private final CrimeCaseService crimeCaseService;

    @PostMapping("/auth/major/crime-case/create")
    @Transactional
    public ResponseEntity<?> createCrimeCase(@RequestBody CrimeIdRequest crimeIdRequest,
                                             Authentication authentication) {
        var series = getSeries(authentication);
        var crimeId = crimeIdRequest.getCrimeId();
        var stateBeforeCreation = crimeCaseService.getStateBeforeCreation(crimeId, series);
        return switch (stateBeforeCreation) {
            case OK -> {
                crimeCaseService.create(crimeId, series);
                yield new ResponseEntity<>(HttpStatus.CREATED);
            }
            case CASE_EXISTS -> new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            case WRONG_DISTRICT -> new ResponseEntity<>(HttpStatus.FORBIDDEN);
            case NO_CRIME -> new ResponseEntity<>(HttpStatus.NOT_FOUND);
        };
    }

    @PatchMapping("/auth/major/crime-case/{crime-id}/update/state/{name}")
    @Transactional
    public ResponseEntity<?> updateCrimeCaseState(@PathVariable("crime-id") Integer crimeId,
                                                  @PathVariable("name") String stateName,
                                                  Authentication authentication) {
        var series = getSeries(authentication);
        CrimeCase.State state;
        try {
            state = EnumConverters.convertToCrimeCaseState(stateName);
        } catch (IllegalEnumValueException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        var stateBeforeUpdate = crimeCaseService.getStateBeforeUpdate(crimeId, series);
        return switch (stateBeforeUpdate) {
            case OK -> {
                crimeCaseService.updateState(crimeId, state);
                yield new ResponseEntity<>(HttpStatus.OK);
            }
            case CANT_CHANGE_CLOSED, NO_CASE -> new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            case NO_CRIME -> new ResponseEntity<>(HttpStatus.NOT_FOUND);
            case ANOTHER_OWNER -> new ResponseEntity<>(HttpStatus.FORBIDDEN);
        };
    }

    @GetMapping("/auth/major/crime-case/my")
    public CasesWithMinPolicemenAndUniqueFullResponse getPrecinctCases(Authentication authentication) {
        var series = getSeries(authentication);
        return crimeCaseService.getCasesWithMinPolicemanAndUniqueFull(series);
    }

    @GetMapping("/auth/minor/crime-case/available")
    public List<BaseCrimeCaseNoStateResponse> getAvailableCases(Authentication authentication) {
        var series = getSeries(authentication);
        return crimeCaseService.getOpenedCasesOnWhichMinorStatusAssigned(series);
    }
}
