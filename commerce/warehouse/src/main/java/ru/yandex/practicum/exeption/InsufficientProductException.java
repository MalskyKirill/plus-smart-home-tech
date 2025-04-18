package ru.yandex.practicum.exeption;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InsufficientProductException extends RuntimeException{
    public InsufficientProductException(String message) {
        super(message);
    }
}
