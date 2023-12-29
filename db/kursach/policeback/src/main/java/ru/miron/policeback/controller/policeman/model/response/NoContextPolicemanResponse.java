package ru.miron.policeback.controller.policeman.model.response;

import lombok.Data;

import java.time.LocalDate;

@Data
public class NoContextPolicemanResponse {
    private Integer id;
    private String name;
    private String series;
    private String rank;
    private LocalDate seriesDateOfIssue;
}
