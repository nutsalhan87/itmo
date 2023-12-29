package ru.miron.policeback.controller.personrel;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import ru.miron.policeback.controller.crime.model.request.CrimeIdRequest;
import ru.miron.policeback.controller.personrel.model.request.PutPersonToCaseRequest;
import ru.miron.policeback.controller.personrel.model.response.BaseCasePersonResponse;
import ru.miron.policeback.controller.personrel.service.PersonRelevantToCaseService;
import ru.miron.policeback.entities.PersonRelevantToCase;
import ru.miron.policeback.exceptions.IllegalEnumValueException;
import ru.miron.policeback.util.EnumConverters;

import java.util.List;

import static ru.miron.policeback.controller.policeman.PolicemanController.getSeries;

@RestController
@RequestMapping("/api/v${api.version}")
@AllArgsConstructor
public class PersonRelevantToCaseController {

    private final PersonRelevantToCaseService personRelevantToCaseService;

    @PutMapping("/auth/minor/person-relevant-to-case/put")
    @Transactional
    public ResponseEntity<?> putPersonToCase(@RequestBody PutPersonToCaseRequest putPersonToCaseRequest,
                                             Authentication authentication) {
        var series = getSeries(authentication);
        PersonRelevantToCase.Relation relation = null;
        try {
            if (putPersonToCaseRequest.getRelation() != null) {
                relation = EnumConverters.convertToPersonCaseRelation(putPersonToCaseRequest.getRelation());
            }
        } catch (IllegalEnumValueException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        var stateBeforePut = personRelevantToCaseService.getStateBeforePut(
                putPersonToCaseRequest.getCrimeId(),
                putPersonToCaseRequest.getPersonId(),
                series
        );
        return switch (stateBeforePut) {
            case TO_ADD -> {
                if (relation == null || putPersonToCaseRequest.getNote() == null) {
                    yield new ResponseEntity<>(HttpStatus.BAD_REQUEST);
                }
                personRelevantToCaseService.addPersonToCase(
                        putPersonToCaseRequest.getCrimeId(),
                        putPersonToCaseRequest.getPersonId(),
                        relation,
                        putPersonToCaseRequest.getNote(),
                        series
                );
                yield new ResponseEntity<>(HttpStatus.CREATED);
            }
            case TO_UPDATE -> {
                personRelevantToCaseService.update(
                        putPersonToCaseRequest.getCrimeId(),
                        putPersonToCaseRequest.getPersonId(),
                        relation,
                        putPersonToCaseRequest.getNote()
                );
                yield new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            case CASE_NOT_ON_WORK, ABOUT_SELF -> new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            case NO_POLICEMAN_CASE, NOT_OWNING_IF_UPDATE, NOT_ASSIGNED_STATE -> new ResponseEntity<>(HttpStatus.FORBIDDEN);
            case NO_CRIME, NO_CASE -> new ResponseEntity<>(HttpStatus.NOT_FOUND);
        };
    }

    @GetMapping("/auth/minor/person-relevant-to-case/in-case/{crime-id}")
    @Transactional
    public ResponseEntity<List<BaseCasePersonResponse>> getCasePeople(@PathVariable("crime-id") Integer crimeId,
                                                                      Authentication authentication) {
        var series = getSeries(authentication);
        var stateBeforeReceiving = personRelevantToCaseService.getStateBeforeReceiving(crimeId, series);
        return switch (stateBeforeReceiving) {
            case OK -> {
                var relatedPeople = personRelevantToCaseService.getRelatedPeople(crimeId);
                yield new ResponseEntity<>(relatedPeople, HttpStatus.OK);
            }
            case NO_POLICEMAN_CASE, NOT_ASSIGNED_STATE -> new ResponseEntity<>(HttpStatus.FORBIDDEN);
            case NO_CRIME, NO_CASE -> new ResponseEntity<>(HttpStatus.NOT_FOUND);
        };
    }
}
