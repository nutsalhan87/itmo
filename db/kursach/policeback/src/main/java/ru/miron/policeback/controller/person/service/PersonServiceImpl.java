package ru.miron.policeback.controller.person.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.miron.policeback.controller.person.model.response.PersonResponse;
import ru.miron.policeback.repositories.PersonRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PersonServiceImpl implements PersonService {

    private PersonRepository personRepository;

    @Override
    public List<PersonResponse> getAllExceptCaller(String series) {
        return personRepository.getAllExceptCaller(series).stream()
                .map(PersonResponse::init)
                .collect(Collectors.toList());
    }
}
