package com.alura.forohub.forohub.errors;

public class NotTokenValidException extends AuthenticationException {
    public NotTokenValidException(String message) {
        super(message);
    }
}
