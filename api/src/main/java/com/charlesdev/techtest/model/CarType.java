package com.charlesdev.techtest.model;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "car_types")
public class CarType {
    @Id
    String id;
    Double pricePerDay;
    @JsonIgnore
    @OneToMany(mappedBy = "type")
    Set<Car> cars;

    public CarType() {
    }

    public CarType(String id, Double pricePerDay) {
        this.id = id;
        this.pricePerDay = pricePerDay;
    }

    public Set<Car> getCars() {
        return cars;
    }

    public void setCars(Set<Car> cars) {
        this.cars = cars;
    }

    public Double getPricePerDay() {
        return pricePerDay;
    }

    public void setPricePerDay(Double pricePerDay) {
        this.pricePerDay = pricePerDay;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
