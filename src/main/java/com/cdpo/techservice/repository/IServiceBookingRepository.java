package com.cdpo.techservice.repository;

import com.cdpo.techservice.model.Booking;

import java.time.LocalDateTime;
import java.util.List;

public interface IServiceBookingRepository {
    long createBooking(List<Long> serviceId, LocalDateTime appointmentTime);

    boolean setCanceledStatusById(Long id);

    Booking updateServiceTimeById(Long id, LocalDateTime appointmentTime);

    Booking updateServiceDicountById(Long id, double discount);

    List<Booking> getAllBooking();

    Booking getBookingById(Long id);
}
