package com.example.taskmanager.persist.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public class AuthRequest {
    private String username;
    private String password;

    @Schema(name = "username", example = "user@mail.com")
    public String getUsername() {
        return username;
    }

    @Schema(name = "password")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
