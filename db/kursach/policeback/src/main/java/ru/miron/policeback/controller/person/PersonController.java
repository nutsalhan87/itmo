package ru.miron.policeback.controller.person;


import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ru.miron.policeback.controller.person.model.request.AddPersonRequest;
import ru.miron.policeback.controller.person.model.response.PersonResponse;
import ru.miron.policeback.controller.person.service.PersonService;

import java.util.List;

import static ru.miron.policeback.controller.policeman.PolicemanController.getSeries;

@RestController
@RequestMapping("/api/v${api.version}")
@AllArgsConstructor
public class PersonController {

    private final PersonService personService;

    @GetMapping("/auth/minor/person/list")
    public List<PersonResponse> getAllPeople(Authentication authentication) {
        var series = getSeries(authentication);
        return personService.getAllExceptCaller(series);
    }
}
