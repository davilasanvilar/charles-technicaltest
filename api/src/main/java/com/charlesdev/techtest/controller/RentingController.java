package com.charlesdev.techtest.controller;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.management.InstanceNotFoundException;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.charlesdev.techtest.exception.CarNotAvailableException;
import com.charlesdev.techtest.form.CarForm;
import com.charlesdev.techtest.form.RentForm;
import com.charlesdev.techtest.model.Booking;
import com.charlesdev.techtest.model.Car;
import com.charlesdev.techtest.model.CarType;
import com.charlesdev.techtest.model.dto.CarTypeAvailability;
import com.charlesdev.techtest.repository.BookingRepository;
import com.charlesdev.techtest.repository.CarRepository;
import com.charlesdev.techtest.repository.CarTypeRepository;
import com.charlesdev.techtest.service.RentingService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")
public class RentingController {

    private final RentingService rentingService;
    private final CarRepository carRepository;
    private final CarTypeRepository carTypeRepository;
    private final BookingRepository bookingRepository;

    public RentingController(RentingService rentingService, CarRepository carRepository,
            CarTypeRepository carTypeRepository, BookingRepository bookingRepository) {
        this.rentingService = rentingService;
        this.carRepository = carRepository;
        this.carTypeRepository = carTypeRepository;
        this.bookingRepository = bookingRepository;
    }

    @GetMapping("/cars")
    public ResponseEntity<List<Car>> listCars() {
        List<Car> carList = carRepository.findAll();
        return ResponseEntity.ok().body(carList);
    }

    @GetMapping("/cars/available")
    public ResponseEntity<List<CarTypeAvailability>> listAvailableCars(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date endDate) {
        List<CarTypeAvailability> carList = rentingService.listAvailableCars(startDate, endDate);
        return ResponseEntity.ok().body(carList);
    }

    @PostMapping("/cars")
    public ResponseEntity<Car> addCar(@RequestBody @Valid CarForm form) {
        Optional<CarType> carType = carTypeRepository.findById(form.getType());
        if (carType.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        Car car = new Car(form.getName(), carType.get());
        Car newCar = carRepository.save(car);
        return ResponseEntity.ok().body(newCar);
    }

    @PostMapping("/cars/rent")
    public ResponseEntity<Booking> rentCar(@RequestBody @Valid RentForm rentForm) {
        try {
            Booking booking = rentingService.rentCar(rentForm);
            return ResponseEntity.ok().body(booking);
        } catch (InstanceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (CarNotAvailableException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @GetMapping("/cars/types")
    public ResponseEntity<List<CarType>> listCarTypes() {
        List<CarType> carTypes = carTypeRepository.findAll();
        return ResponseEntity.ok().body(carTypes);
    }

    @GetMapping("/bookings")
    public ResponseEntity<List<Booking>> listBookings(@RequestParam UUID userId) {
        List<Booking> bookingList = bookingRepository.findByUserId(userId);
        return ResponseEntity.ok().body(bookingList);
    }

    @PostMapping("/bookings/{bookingId}/return")
    public ResponseEntity<Booking> returnBooking(@PathVariable(value = "bookingId") UUID bookingId) {
        try {
            Booking booking = rentingService.returnCar(bookingId);
            return ResponseEntity.ok().body(booking);
        } catch (InstanceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
