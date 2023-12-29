package ru.miron.policeback.controller.note;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import ru.miron.policeback.controller.note.model.request.NoteCreateRequest;
import ru.miron.policeback.controller.note.model.response.CrimeNoteResponse;
import ru.miron.policeback.controller.note.service.NoteService;

import java.util.List;

import static ru.miron.policeback.controller.policeman.PolicemanController.getSeries;

@RestController
@RequestMapping("/api/v${api.version}")
@AllArgsConstructor
public class NoteController {

    private final NoteService noteService;

    @PostMapping("/auth/minor/note/create")
    @Transactional
    public ResponseEntity<?> createCrimeNote(@RequestBody NoteCreateRequest noteCreateRequest,
                                             Authentication authentication) {
        var series = getSeries(authentication);
        var crimeId = noteCreateRequest.getCrimeId();
        var stateBeforeCreate = noteService.getStateBeforeCreation(series, crimeId);
        return switch (stateBeforeCreate) {
            case OK -> {
                noteService.create(crimeId, series, noteCreateRequest.getText());
                yield new ResponseEntity<>(HttpStatus.CREATED);
            }
            case NO_POLICEMAN_CASE, NOT_ASSIGNED_STATE -> new ResponseEntity<>(HttpStatus.FORBIDDEN);
            case NO_CRIME, NO_CASE -> new ResponseEntity<>(HttpStatus.NOT_FOUND);
        };
    }

    @GetMapping("/auth/minor/note/in-case/{crime-id}")
    @Transactional
    public ResponseEntity<List<CrimeNoteResponse>> getCaseNotes(@PathVariable("crime-id") Integer crimeId,
                                                                Authentication authentication) {
        var series = getSeries(authentication);
        var stateBeforeReceiving = noteService.getStateBeforeOnCaseReceivingByMinor(series, crimeId);
        return switch (stateBeforeReceiving) {
            case OK -> new ResponseEntity<>(noteService.getCaseNotes(crimeId), HttpStatus.OK);
            case NO_POLICEMAN_CASE, NOT_ASSIGNED_STATE -> new ResponseEntity<>(HttpStatus.FORBIDDEN);
            case NO_CRIME, NO_CASE -> new ResponseEntity<>(HttpStatus.NOT_FOUND);
        };
    }
}
