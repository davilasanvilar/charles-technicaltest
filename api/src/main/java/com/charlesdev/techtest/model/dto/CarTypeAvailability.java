package com.charlesdev.techtest.model.dto;

import com.charlesdev.techtest.model.CarType;

public class CarTypeAvailability {
    private CarType carType;
    private long availableCars;

    public CarTypeAvailability() {
    }

    public CarTypeAvailability(CarType carType, long availableCars) {
        this.carType = carType;
        this.availableCars = availableCars;
    }

    public CarType getCarType() {
        return carType;
    }

    public void setCarType(CarType carType) {
        this.carType = carType;
    }

    public long getAvailableCars() {
        return availableCars;
    }

    public void setAvailableCars(long availableCars) {
        this.availableCars = availableCars;
    }

}
