package ru.miron.policeback.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class IllegalEnumValueException extends RuntimeException {
    public IllegalEnumValueException() {
        super();
    }

    public IllegalEnumValueException(String message) {
        super(message);
    }
}
