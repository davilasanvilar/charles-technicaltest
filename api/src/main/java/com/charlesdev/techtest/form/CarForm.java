package com.charlesdev.techtest.form;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class CarForm {
    @NotNull(message = "Car model is required")
    @NotEmpty(message = "Car model is required")
    private String name;

    @NotNull(message = "Car type is required")
    @NotEmpty(message = "Car type is required")
    private String type;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
