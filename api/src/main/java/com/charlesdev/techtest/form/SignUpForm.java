package com.charlesdev.techtest.form;

import jakarta.validation.constraints.NotNull;

public class SignUpForm {
    @NotNull(message = "Username is required")
    private String username;

    public String getUsername() {
        return username;
    }
}
