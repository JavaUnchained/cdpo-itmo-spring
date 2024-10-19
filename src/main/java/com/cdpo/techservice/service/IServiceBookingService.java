package com.cdpo.techservice.service;

import com.cdpo.techservice.dto.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface IServiceBookingService {

    long createBooking(BookingRequestDTO requestBooking);

    BookingResponseDTO getBookingById(Long id);

    List<BookingResponseDTO> getAllFilteredBy(BookingStateDTO stateDTO);
    List<BookingResponseDTO> getAllFilteredBy(LocalDateTime time);

    List<BookingResponseDTO> getAllBookings();

    void cancelBooking(Long id);

    BookingResponseDTO updateBooking(Long id, BookingUpdateTimeDTO updateDTO);
    BookingResponseDTO updateBooking(Long id, BookingUpdateDiscountDTO updateDTO);

    List<BookingResponseDTO> getAllProvidedBookings();

    List<RevenueDTO> calculateRevenue(LocalDate from, LocalDate to);
}
