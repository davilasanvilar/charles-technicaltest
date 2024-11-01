package com.charlesdev.techtest.form;

import java.util.UUID;

import jakarta.validation.constraints.NotNull;

public class ReturnForm {
    @NotNull(message = "Booking ID is required")
    private UUID bookingId;

    public UUID getBookingId() {
        return bookingId;
    }

}
