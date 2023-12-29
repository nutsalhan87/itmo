package ru.miron.policeback.controller.personrel.model.request;

import lombok.Data;

@Data
public class PutPersonToCaseRequest {
    private Integer crimeId;
    private Integer personId;
    private String relation;
    private String note;
}
