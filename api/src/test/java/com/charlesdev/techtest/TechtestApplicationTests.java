package com.charlesdev.techtest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.management.InstanceNotFoundException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.charlesdev.techtest.exception.CarNotAvailableException;
import com.charlesdev.techtest.form.RentForm;
import com.charlesdev.techtest.model.Booking;
import com.charlesdev.techtest.model.Car;
import com.charlesdev.techtest.model.CarType;
import com.charlesdev.techtest.model.User;
import com.charlesdev.techtest.repository.BookingRepository;
import com.charlesdev.techtest.repository.CarRepository;
import com.charlesdev.techtest.repository.UserRepository;
import com.charlesdev.techtest.service.AuthService;
import com.charlesdev.techtest.service.RentingService;

@ExtendWith(MockitoExtension.class)
class TechtestApplicationTests {

    @InjectMocks
    private AuthService authService;

    @InjectMocks
    private RentingService rentingService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private CarRepository carRepository;

    @Mock
    private BookingRepository bookingRepository;

    private static final String REGISTERED_USER = "registeredUser";
    private static final String NOT_REGISTERED_USER = "notRegisteredUser";

    @Test
    public void testLoginRegisteredUser_OK() throws InstanceNotFoundException {
        when(userRepository.findByUsername(REGISTERED_USER)).thenReturn(new User(REGISTERED_USER));

        User resultUser = authService.signIn(REGISTERED_USER);
        assertEquals(resultUser.getUsername(), REGISTERED_USER);
    }

    @Test
    public void testLoginNotRegisteredUser_NOTFOUND() throws InstanceNotFoundException {
        when(userRepository.findByUsername(NOT_REGISTERED_USER)).thenReturn(null);
        assertThrows(InstanceNotFoundException.class,
                () -> authService.signIn(NOT_REGISTERED_USER));
    }

    @Test
    public void testRegisterUser_OK() {
        when(userRepository.findByUsername(NOT_REGISTERED_USER)).thenReturn(null);
        authService.signUp(NOT_REGISTERED_USER);
        ArgumentCaptor<User> argumentCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository, times(1)).save(argumentCaptor.capture());
        assertEquals(argumentCaptor.getValue().getUsername(), NOT_REGISTERED_USER);
    }

    @Test
    public void testRegisterUser_CONFLICT() {
        when(userRepository.findByUsername(REGISTERED_USER)).thenReturn(new User(REGISTERED_USER));
        assertThrows(IllegalArgumentException.class,
                () -> authService.signUp(REGISTERED_USER));
    }

    private final UUID USER_ID = UUID.randomUUID();
    private final String CAR_TYPE = "suv";
    private final Date START_DATE = new Date();
    private final Date END_DATE;
    private final Double PRICE_PER_DAY = 100.0;
    private final String CAR_NAME = "Ford";
    private final Car CAR = new Car(CAR_NAME, new CarType(CAR_TYPE, PRICE_PER_DAY));
    private final int N_DAYS_RENT = 2;

    {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, N_DAYS_RENT);
        END_DATE = cal.getTime();
    }
    private final RentForm RENT_FORM = new RentForm(CAR_TYPE, USER_ID, START_DATE, END_DATE);

    @Test
    public void testRentCar_NOTAVAILABLE() {
        when(carRepository.findAvailableCarsByTypeAndDate(CAR_TYPE, START_DATE, END_DATE))
                .thenReturn(new ArrayList<>());
        assertThrows(CarNotAvailableException.class,
                () -> rentingService.rentCar(RENT_FORM));
    }

    @Test
    public void testRentCar_OK() throws InstanceNotFoundException, CarNotAvailableException {
        List<Car> availableCars = new ArrayList<>();
        availableCars.add(CAR);
        when(carRepository.findAvailableCarsByTypeAndDate(CAR_TYPE, START_DATE, END_DATE))
                .thenReturn(availableCars);
        User user = new User(REGISTERED_USER);
        user.setId(USER_ID);
        when(userRepository.findById(USER_ID)).thenReturn(java.util.Optional.of(user));
        rentingService.rentCar(RENT_FORM);
        ArgumentCaptor<Booking> argumentCaptor = ArgumentCaptor.forClass(Booking.class);
        verify(bookingRepository, times(1)).save(argumentCaptor.capture());
        assertEquals(argumentCaptor.getValue().getCar().getName(), CAR_NAME);
        assertEquals(argumentCaptor.getValue().getUser().getUsername(), REGISTERED_USER);
        assertEquals(argumentCaptor.getValue().getStartDate(), START_DATE);
        assertEquals(argumentCaptor.getValue().getEndDate(), END_DATE);
        assertEquals(argumentCaptor.getValue().getExpectedPrice(), PRICE_PER_DAY * N_DAYS_RENT);
        assertEquals(argumentCaptor.getValue().getTotalPrice(), null);
        assertEquals(argumentCaptor.getValue().getReturnDate(), null);

    }

    @Test
    public void testReturnCar_NOTFOUND() {
        when(bookingRepository.findById(USER_ID)).thenReturn(java.util.Optional.empty());
        assertThrows(InstanceNotFoundException.class,
                () -> rentingService.returnCar(USER_ID));
    }

    @Test
    public void testReturnCar_OK() throws InstanceNotFoundException {
        Booking booking = new Booking(CAR, new User(REGISTERED_USER), START_DATE, END_DATE,
                PRICE_PER_DAY * N_DAYS_RENT);
        booking.setId(USER_ID);
        when(bookingRepository.findById(USER_ID)).thenReturn(java.util.Optional.of(booking));
        rentingService.returnCar(USER_ID);
        ArgumentCaptor<Booking> argumentCaptor = ArgumentCaptor.forClass(Booking.class);
        verify(bookingRepository, times(1)).save(argumentCaptor.capture());
        assertEquals(argumentCaptor.getValue().getReturnDate().getTime(), new Date().getTime(), 1000);
        assertEquals(argumentCaptor.getValue().getTotalPrice(), PRICE_PER_DAY * N_DAYS_RENT);
    }

}
