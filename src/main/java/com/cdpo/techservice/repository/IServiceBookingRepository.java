package com.cdpo.techservice.repository;

import com.cdpo.techservice.model.Booking;

import java.time.LocalDateTime;
import java.util.List;

public interface IServiceBookingRepository {
    long createBooking(long serviceId, LocalDateTime appointmentTime);

    boolean deleteBookingById(Long id);

    Booking updateServiceById(Long id, LocalDateTime appointmentTime);

    List<Booking> getAllBooking();

    Booking getBookingById(Long id);
}
