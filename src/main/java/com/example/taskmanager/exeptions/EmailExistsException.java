package com.example.taskmanager.exeptions;

import jakarta.validation.constraints.NotEmpty;

public class EmailExistsException extends RuntimeException {

    private final String message;

    public EmailExistsException(@NotEmpty String message) {
        super("Email уже используется: ");
        this.message = message;
    }

    public String getErrorMessage() {
        return this.getMessage() + message;
    }
}
