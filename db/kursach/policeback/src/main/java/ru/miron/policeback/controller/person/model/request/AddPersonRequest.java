package ru.miron.policeback.controller.person.model.request;

import lombok.Data;

import java.time.LocalDate;

@Data
public class AddPersonRequest {
    private String name;
    private LocalDate birthdate;
    private String race;
}
