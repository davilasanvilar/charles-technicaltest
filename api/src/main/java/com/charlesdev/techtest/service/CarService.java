package com.charlesdev.techtest.service;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.charlesdev.techtest.model.Car;
import com.charlesdev.techtest.repository.CarRepository;
import jakarta.transaction.Transactional;

@Service
@Transactional(rollbackOn = Exception.class)
public class CarService {
    private CarRepository carRepository;

    public CarService(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    public List<Car> listCars() {
        return carRepository.findAll(Sort.by(Sort.Direction.ASC, "pricePerDay"));
    }
}
