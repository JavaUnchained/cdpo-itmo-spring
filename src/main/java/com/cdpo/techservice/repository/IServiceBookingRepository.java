package com.cdpo.techservice.repository;

import com.cdpo.techservice.model.Booking;
import com.cdpo.techservice.model.BookingState;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface IServiceBookingRepository extends JpaRepository<Booking,Long> {
    List<Booking> findByState(BookingState state);

    List<Booking> findByAppointmentTime(LocalDateTime appointmentTime);

    List<Booking> findByAppointmentTimeLessThan(LocalDateTime appointmentTime);

    List<Booking> findByAppointmentTimeGreaterThan(LocalDateTime appointmentTime);

    List<Booking> findByAppointmentTimeBetweenOrderByAppointmentTime(LocalDateTime from, LocalDateTime to);

    @Transactional
    @Modifying
    @Query("update Booking b set b.state = ?1 where b.id = ?2")
    int updateStateById(BookingState state, long id);

    @Transactional
    @Modifying
    @Query("update Booking b set b.appointmentTime = ?1 where b.id = ?2")
    void updateAppointmentTimeById(LocalDateTime appointmentTime, long id);

    @Transactional
    @Modifying
    @Query("update Booking b set b.discountPercent = ?1 where b.id = ?2")
    void updateDiscountPercentById(Double discountPercent, long id);

}
