package ru.yandex.practicum.exeption;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class MissingUsernameException extends RuntimeException {
    public MissingUsernameException(String message) {
        super(message);
    }
}
