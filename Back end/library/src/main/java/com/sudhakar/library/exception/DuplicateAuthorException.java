package com.sudhakar.library.exception;

public class DuplicateAuthorException extends RuntimeException {
    public DuplicateAuthorException(String message) {
        super(message);
    }
}