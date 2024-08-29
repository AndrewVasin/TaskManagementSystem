package com.example.taskmanager.exeptions;

public class UserNotFoundException extends RuntimeException {

    private String message;

    public UserNotFoundException() {
    }

    public UserNotFoundException(String message) {
        super("Пользователь не найден: ");
        this.message = message;
    }

    public UserNotFoundException(Long message) {
        super("Пользователь не найден: ");
        this.message = String.valueOf(message);
    }

    public String getErrorMessage(){
        return this.getMessage() + message;
    }
}
