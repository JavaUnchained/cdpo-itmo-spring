package com.cdpo.techservice.service;

import com.cdpo.techservice.dto.BookingRequestDTO;
import com.cdpo.techservice.dto.BookingResponseDTO;
import com.cdpo.techservice.dto.BookingUpdateDTO;
import com.cdpo.techservice.dto.ServiceResponseDTO;
import com.cdpo.techservice.model.Booking;
import com.cdpo.techservice.model.Service;
import com.cdpo.techservice.repository.IServiceBookingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Primary
@org.springframework.stereotype.Service
@RequiredArgsConstructor
public class ServiceBookingService implements IServiceBookingService {
    private final IServiceBookingRepository bookingRepository;

    @Override
    public long createBooking(BookingRequestDTO requestBooking) {
        return bookingRepository
                .createBooking(requestBooking.serviceId(), requestBooking.appointmentTime());
    }

    @Override
    public Optional<BookingResponseDTO> getBookingById(Long id) {
        return Optional.ofNullable(bookingRepository.getBookingById(id))
                .map(ServiceBookingService::bookingToResponseDTO);
    }

    @Override
    public List<BookingResponseDTO> getAllBookings() {
        return bookingRepository.getAllBooking().stream()
                .map(ServiceBookingService::bookingToResponseDTO).collect(Collectors.toList());

    }

    @Override
    public boolean deleteBooking(Long id) {
        return bookingRepository.deleteBookingById(id);
    }

    @Override
    public Optional<BookingResponseDTO> updateBooking(Long id, BookingUpdateDTO updateDTO) {
        return Optional.ofNullable(
                bookingRepository.updateServiceById(id, updateDTO.appointmentTime()))
                .map(ServiceBookingService::bookingToResponseDTO);
    }

    @Override
    public List<BookingResponseDTO> getAllProvidedBookings() {
        List<BookingResponseDTO> allBookings = getAllBookings();
        if(allBookings.isEmpty()) return allBookings;
        return allBookings.stream()
                .filter(b -> b.appointmentTime().isBefore(LocalDateTime.now()))
                .collect(Collectors.toList());
    }

    private static BookingResponseDTO bookingToResponseDTO(Booking booking) {
        Service service = booking.getService();
        return new BookingResponseDTO(
                booking.getId(),
                new ServiceResponseDTO(service.getId(), service.getName(), service.getDescription()),
                booking.getAppointmentTime()
        );
    }
}
