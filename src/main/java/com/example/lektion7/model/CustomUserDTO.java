package com.example.lektion7.model;

public class CustomUserDTO {

    private final String username;
    private final String password;

    public CustomUserDTO(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
