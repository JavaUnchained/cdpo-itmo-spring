package com.cdpo.techservice.dto;

import com.cdpo.techservice.model.BookingState;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * DTO for {@link com.cdpo.techservice.model.Booking}
 */
public record BookingDTO(List<Long> serviceIds,
                         LocalDateTime appointmentTime,
                         Double discountPercent,
                         BookingState state,
                         String username) {
}