package com.charlesdev.techtest.repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.charlesdev.techtest.model.Car;
import com.charlesdev.techtest.model.dto.CarTypeAvailability;

import jakarta.persistence.LockModeType;

@Repository
public interface CarRepository extends JpaRepository<Car, UUID> {

        @Query("SELECT new com.charlesdev.techtest.model.dto.CarTypeAvailability(c.type, " +
                        "       (SELECT COUNT(c2) FROM Car c2 WHERE c.type.id = c2.type.id AND NOT EXISTS (" +
                        "           SELECT b FROM Booking b WHERE " +
                        "           c2.id = b.car.id AND b.returnDate IS NULL " +
                        "           AND (" +
                        "               :startDate >= b.startDate AND :startDate<=b.endDate  " +
                        "               OR " +
                        "               :endDate >= b.startDate AND :endDate<=b.endDate  " +
                        "               OR " +
                        "               :startDate <= b.startDate AND :endDate>=b.endDate  " +
                        "            )" +
                        "        ) ) )" +
                        "FROM Car c GROUP BY c.type, c.type.id")
        List<CarTypeAvailability> findAvailableCars(Date startDate, Date endDate);

        @Query("SELECT c FROM Car c WHERE c.type.id=:carType AND NOT EXISTS (" +
                        "           SELECT b FROM Booking b WHERE " +
                        "           c.id = b.car.id AND b.returnDate IS NULL " +
                        "           AND (" +
                        "               :startDate >= b.startDate AND :startDate<=b.endDate  " +
                        "               OR " +
                        "               :endDate >= b.startDate AND :endDate<=b.endDate  " +
                        "               OR " +
                        "               :startDate <= b.startDate AND :endDate>=b.endDate  " +
                        "            )" +
                        ")")
        @Lock(LockModeType.PESSIMISTIC_WRITE)
        List<Car> findAvailableCarsByTypeAndDate(String carType, Date startDate, Date endDate);

}
