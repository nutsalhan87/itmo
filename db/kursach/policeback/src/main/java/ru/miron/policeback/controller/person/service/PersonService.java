package ru.miron.policeback.controller.person.service;

import ru.miron.policeback.controller.person.model.response.PersonResponse;

import java.util.List;

public interface PersonService {
    List<PersonResponse> getAllExceptCaller(String series);
}
