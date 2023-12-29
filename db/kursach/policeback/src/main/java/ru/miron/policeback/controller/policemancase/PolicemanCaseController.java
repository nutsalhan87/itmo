package ru.miron.policeback.controller.policemancase;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import ru.miron.policeback.controller.policemancase.model.request.PutPolicemanCaseRequest;
import ru.miron.policeback.controller.policemancase.service.PolicemanCaseService;
import ru.miron.policeback.entities.PolicemanCase;
import ru.miron.policeback.exceptions.IllegalEnumValueException;
import ru.miron.policeback.util.EnumConverters;

import static ru.miron.policeback.controller.policeman.PolicemanController.getSeries;

@RestController
@RequestMapping("/api/v${api.version}")
@AllArgsConstructor
public class PolicemanCaseController {

    private final PolicemanCaseService policemanCaseService;

    @PutMapping("/auth/major/policeman-case/put")
    @Transactional
    public ResponseEntity<?> policemanOnCasePut(@RequestBody PutPolicemanCaseRequest putPolicemanCaseRequest,
                                                Authentication authentication) {
        var ownerSeries = getSeries(authentication);
        var crimeId = putPolicemanCaseRequest.getCrimeId();
        var slaveId = putPolicemanCaseRequest.getPolicemanId();
        PolicemanCase.Status onCaseStatus;
        try {
            if (putPolicemanCaseRequest.getOnCaseStatus() != null) {
                onCaseStatus = EnumConverters.convertToPolicemanOnCaseStatus(putPolicemanCaseRequest.getOnCaseStatus());
            } else {
                onCaseStatus = PolicemanCase.Status.ASSIGNED;
            }
        } catch (IllegalEnumValueException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        var stateBeforePut = policemanCaseService.getStateBeforePut(crimeId, slaveId, ownerSeries);
        System.out.println(stateBeforePut);
        return switch (stateBeforePut) {
            case TO_ADD -> {
                policemanCaseService.addOnCase(crimeId, slaveId, onCaseStatus, ownerSeries);
                yield new ResponseEntity<>(HttpStatus.CREATED);
            }
            case TO_UPDATE -> {
                policemanCaseService.update(crimeId, slaveId, onCaseStatus);
                yield new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            case CASE_NOT_ON_WORK -> new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            case DOES_NOT_OWN -> new ResponseEntity<>(HttpStatus.FORBIDDEN);
            case NO_CRIME, NO_SLAVE, NO_CASE -> new ResponseEntity<>(HttpStatus.NOT_FOUND);
        };
    }
}
