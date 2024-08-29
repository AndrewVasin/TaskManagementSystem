package com.example.taskmanager.exeptions;

public class TaskNotFoundException extends RuntimeException {
    public TaskNotFoundException() {
        super("Task with this id not found!");
    }
}
