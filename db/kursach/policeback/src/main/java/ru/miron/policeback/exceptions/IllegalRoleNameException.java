package ru.miron.policeback.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class IllegalRoleNameException extends RuntimeException {
    public IllegalRoleNameException(String message) {
        super(message);
    }
}
