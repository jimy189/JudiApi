package com.br.judiapi.exception;

public class BadRequestExceptionCustom extends RuntimeException {
    public BadRequestExceptionCustom(String message) {
        super(message);
    }
}
