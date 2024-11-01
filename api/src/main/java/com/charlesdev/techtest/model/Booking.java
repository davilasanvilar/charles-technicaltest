package com.charlesdev.techtest.model;

import java.util.Date;


import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(name = "bookings")
public class Booking extends BaseEntity {

    @ManyToOne(optional = false)
    @JoinColumn(name = "car_id")
    Car car;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id")
    User user;
    
    @Temporal(TemporalType.DATE)
    Date startDate;


    @Temporal(TemporalType.DATE)
    Date endDate;


    @Temporal(TemporalType.DATE)
    Date returnDate;
    Double expectedPrice;
    Double totalPrice;

    public Booking() {
    }

    public Booking(Car car, User user, Date startDate, Date endDate, Double expectedPrice) {
        this.car = car;
        this.user = user;
        this.startDate = startDate;
        this.endDate = endDate;
        this.expectedPrice = expectedPrice;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }

    public Double getExpectedPrice() {
        return expectedPrice;
    }

    public void setExpectedPrice(Double expectedPrice) {
        this.expectedPrice = expectedPrice;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }
}
