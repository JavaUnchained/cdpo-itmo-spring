package com.cdpo.techservice.service;

import com.cdpo.techservice.dto.BookingRequestDTO;
import com.cdpo.techservice.dto.BookingResponseDTO;
import com.cdpo.techservice.dto.BookingUpdateDTO;

import java.util.List;
import java.util.Optional;

public interface IServiceBookingService {

    long createBooking(BookingRequestDTO requestBooking);

    Optional<BookingResponseDTO> getBookingById(Long id);

    default Optional<List<BookingResponseDTO>> getBookingByIdAsList(long id) {
        return getBookingById(id).map(List::of);
    }

    List<BookingResponseDTO> getAllBookings();

    boolean deleteBooking(Long id);

    Optional<BookingResponseDTO> updateBooking(Long id, BookingUpdateDTO updateDTO);

    List<BookingResponseDTO> getAllProvidedBookings();
}
