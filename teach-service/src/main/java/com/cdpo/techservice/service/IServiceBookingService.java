package com.cdpo.techservice.service;

import com.cdpo.techservice.dto.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface IServiceBookingService {

    long createBooking(BookingRequestDTO requestBooking, String username);

    BookingResponseDTO getBookingById(Long id, String username);

    List<BookingResponseDTO> getAllFilteredBy(BookingStateDTO stateDTO, String username);
    List<BookingResponseDTO> getAllFilteredBy(LocalDateTime time);

    List<BookingResponseDTO> getAllBookings(String username);

    void cancelBooking(Long id);

    BookingResponseDTO updateBooking(Long id, BookingUpdateTimeDTO updateDTO, String username);
    BookingResponseDTO updateBooking(Long id, BookingUpdateDiscountDTO updateDTO);

    List<BookingResponseDTO> getAllProvidedBookings(String username);

    List<RevenueDTO> calculateRevenue(LocalDate from, LocalDate to);

    BookingResponseDTO updateBooking(long id, BookingDTO updateDTO);

    List<BookingMetricRequestDTO> getCompletedBookings();
}
