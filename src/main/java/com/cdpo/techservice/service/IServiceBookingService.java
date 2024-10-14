package com.cdpo.techservice.service;

import com.cdpo.techservice.dto.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface IServiceBookingService {

    long createBooking(BookingRequestDTO requestBooking);

    Optional<BookingResponseDTO> getBookingById(Long id);

    List<BookingResponseDTO> getAllFilteredBy(BookingStateDTO stateDTO);
    List<BookingResponseDTO> getAllFilteredBy(LocalDateTime time);

    List<BookingResponseDTO> getAllBookings();

    boolean cancelBooking(Long id);

    Optional<BookingResponseDTO> updateBooking(Long id, BookingUpdateTimeDTO updateDTO);
    Optional<BookingResponseDTO> updateBooking(Long id, BookingUpdateDiscountDTO updateDTO);

    List<BookingResponseDTO> getAllProvidedBookings();
}
