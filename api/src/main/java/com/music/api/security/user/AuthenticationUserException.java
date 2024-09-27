package com.music.api.security.user;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class AuthenticationUserException extends RuntimeException {

    private String message;
    private HttpStatus status;

    public AuthenticationUserException(String message, HttpStatus status) {
        super(message);
        this.message = message;
        this.status = status;
    }
}
