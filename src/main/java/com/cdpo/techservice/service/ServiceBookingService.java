package com.cdpo.techservice.service;

import com.cdpo.techservice.dto.*;
import com.cdpo.techservice.model.Booking;
import com.cdpo.techservice.model.Service;
import com.cdpo.techservice.repository.IServiceBookingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Primary
@org.springframework.stereotype.Service
@RequiredArgsConstructor
public class ServiceBookingService implements IServiceBookingService {
    private final IServiceBookingRepository bookingRepository;

    @Override
    public long createBooking(BookingRequestDTO requestBooking) {
        return bookingRepository
                .createBooking(requestBooking.serviceIds(), requestBooking.appointmentTime());
    }

    @Override
    public Optional<BookingResponseDTO> getBookingById(Long id) {
        return Optional.ofNullable(bookingRepository.getBookingById(id))
                .map(ServiceBookingService::bookingToResponseDTO);
    }

    @Override
    public List<BookingResponseDTO> getAllFilteredBy(BookingStateDTO stateDTO) {
        Stream<Booking> bookingStream = bookingRepository
                .getAllBooking().stream().filter(b -> b.getState().name().equals(stateDTO.name()));
        return composeToDto(bookingStream);
    }

    @Override
    public List<BookingResponseDTO> getAllFilteredBy(LocalDateTime time) {
        Stream<Booking> bookingStream = bookingRepository
                .getAllBooking().stream().filter(b -> b.getAppointmentTime().isEqual(time));
        return composeToDto(bookingStream);
    }

    @Override
    public List<BookingResponseDTO> getAllBookings() {
        return composeToDto(bookingRepository.getAllBooking().stream());
    }

    private List<BookingResponseDTO> composeToDto(Stream<Booking> bookingStream) {
        return bookingStream.map(ServiceBookingService::bookingToResponseDTO)
                .sorted(Comparator.comparing(BookingResponseDTO::appointmentTime))
                .collect(Collectors.toList());
    }

    @Override
    public boolean cancelBooking(Long id) {
        return bookingRepository.setCanceledStatusById(id);
    }

    @Override
    public Optional<BookingResponseDTO> updateBooking(Long id, BookingUpdateTimeDTO updateDTO) {
        return Optional.ofNullable(
                bookingRepository.updateServiceTimeById(id, updateDTO.appointmentTime()))
                .map(ServiceBookingService::bookingToResponseDTO);
    }

    @Override
    public Optional<BookingResponseDTO> updateBooking(Long id, BookingUpdateDiscountDTO updateDTO) {
        return Optional.ofNullable(
                        bookingRepository.updateServiceDicountById(id, updateDTO.discountPercent()))
                .map(ServiceBookingService::bookingToResponseDTO);

    }

    @Override
    public List<BookingResponseDTO> getAllProvidedBookings() {
        List<BookingResponseDTO> allBookings = getAllBookings();
        if(allBookings.isEmpty()) return allBookings;
        return allBookings.stream()
                .filter(b -> b.appointmentTime().isBefore(LocalDateTime.now()))
                /**
                 * Сейчас не предусмотрена смены статуса на DONE, поэтому сейчас наглядней по времени
                 */
                .collect(Collectors.toList());
    }

    private static BookingResponseDTO bookingToResponseDTO(Booking booking) {
        List<Service> service = booking.getServices();
        return new BookingResponseDTO(
                booking.getId(),
                service.stream()
                        .map(s ->
                                new ServiceResponseDTO(
                                        s.getId(),
                                        s.getName(),
                                        s.getDescription(),
                                        s.getDuration(),
                                        s.getPrice())
                        ).collect(Collectors.toList()),
                booking.getAppointmentTime(),
                booking.getDiscountPercent(),
                BookingStateDTO.valueOf(booking.getState().name())
        );
    }
}
