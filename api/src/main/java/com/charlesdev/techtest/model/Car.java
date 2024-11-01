package com.charlesdev.techtest.model;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "cars")
public class Car extends BaseEntity {
    String name;
    @ManyToOne(optional = false)
    @JoinColumn(name = "type_id")
    CarType type;
    @JsonIgnore
    @OneToMany(mappedBy = "car")
    Set<Booking> bookings;

    public Car() {
    }

    public Car(String name, CarType type) {
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CarType getType() {
        return type;
    }

    public void setType(CarType type) {
        this.type = type;
    }

    public Set<Booking> getBookings() {
        return bookings;
    }

    public void setBookings(Set<Booking> bookings) {
        this.bookings = bookings;
    }
}
