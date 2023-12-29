package ru.miron.policeback.controller.policemancase.model.request;

import lombok.Data;

@Data
public class PutPolicemanCaseRequest {
    private Integer policemanId;
    private Integer crimeId;
    private String onCaseStatus;
}
