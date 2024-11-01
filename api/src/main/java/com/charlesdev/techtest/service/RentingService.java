package com.charlesdev.techtest.service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.management.InstanceNotFoundException;

import org.springframework.stereotype.Service;

import com.charlesdev.techtest.exception.CarNotAvailableException;
import com.charlesdev.techtest.form.RentForm;
import com.charlesdev.techtest.model.Booking;
import com.charlesdev.techtest.model.Car;
import com.charlesdev.techtest.model.User;
import com.charlesdev.techtest.model.dto.CarTypeAvailability;
import com.charlesdev.techtest.repository.BookingRepository;
import com.charlesdev.techtest.repository.CarRepository;
import com.charlesdev.techtest.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional(rollbackOn = Exception.class)
public class RentingService {
    private BookingRepository bookingRepository;
    private CarRepository carRepository;
    private UserRepository userRepository;

    // Multiplier of the price per day to calculate the penalty
    private final double PENALTY_PER_DAY = 2;

    public RentingService(BookingRepository bookingRepository, CarRepository carRepository,
            UserRepository userRepository) {
        this.bookingRepository = bookingRepository;
        this.carRepository = carRepository;
        this.userRepository = userRepository;
    }

    public List<CarTypeAvailability> listAvailableCars(Date startDate, Date endDate) {
        return carRepository.findAvailableCars(startDate, endDate);
    }

    public Booking rentCar(RentForm form) throws InstanceNotFoundException, CarNotAvailableException {
        // TODO: Optimistic locking for car availability
        List<Car> availableCars = carRepository.findAvailableCarsByTypeAndDate(form.getCarType(), form.getStartDate(),
                form.getEndDate());
        if (availableCars.isEmpty()) {
            throw new CarNotAvailableException("No cars available");
        }
        Car car = availableCars.get(0);
        User user = userRepository.findById(form.getUserId())
                .orElseThrow(() -> new InstanceNotFoundException("User not found"));
        int totalDays = (int) Math
                .ceil((form.getEndDate().getTime() - form.getStartDate().getTime()) / 1000 / 60 / 60
                        / 24);
        double totalPrice = car.getType().getPricePerDay() * totalDays;
        Booking booking = new Booking(
                car,
                user,
                form.getStartDate(),
                form.getEndDate(),
                totalPrice);
        return bookingRepository.save(booking);
    }

    public Booking returnCar(UUID bookingId) throws InstanceNotFoundException {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new InstanceNotFoundException("Booking not found"));
        booking.setReturnDate(new Date());
        double penaltyFee = 0;
        // If the car is returned after the expected return date, calculate the penalty
        // fee
        if (booking.getReturnDate().after(booking.getEndDate())) {
            int daysOverdue = (int) Math
                    .ceil((booking.getReturnDate().getTime() - booking.getEndDate().getTime()) / 1000
                            / 60 / 60
                            / 24);
            penaltyFee = booking.getCar().getType().getPricePerDay() * daysOverdue * PENALTY_PER_DAY;
        }
        double totalPrice = booking.getExpectedPrice() + penaltyFee;
        booking.setTotalPrice(totalPrice);
        return bookingRepository.save(booking);
    }

    public List<Booking> listBookings(UUID userId) {
        return bookingRepository.findByUserId(userId);
    }
}
