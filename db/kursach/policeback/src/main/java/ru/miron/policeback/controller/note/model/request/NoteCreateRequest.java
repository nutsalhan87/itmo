package ru.miron.policeback.controller.note.model.request;

import lombok.Data;

@Data
public class NoteCreateRequest {
    private Integer crimeId;
    private String text;
}
