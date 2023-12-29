package ru.miron.policeback.controller.auth.model.request;

import lombok.Data;

@Data
public class LoginRequest {
    private String series;
    private String password;
}
