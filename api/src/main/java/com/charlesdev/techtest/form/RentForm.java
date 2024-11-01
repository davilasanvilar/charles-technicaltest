package com.charlesdev.techtest.form;

import java.util.Date;
import java.util.UUID;

import jakarta.validation.constraints.NotNull;

public class RentForm {
    @NotNull(message = "Car Type is required")
    private String carType;
    @NotNull(message = "User ID is required")
    private UUID userId;
    @NotNull(message = "Start date is required")
    private Date startDate;
    @NotNull(message = "End date is required")
    private Date endDate;

    public RentForm() {
    }

    public RentForm(String carType, UUID userId, Date startDate, Date endDate) {
        this.carType = carType;
        this.userId = userId;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public String getCarType() {
        return carType;
    }

    public UUID getUserId() {
        return userId;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

}
