package com.cdpo.techservice.mapper;

import com.cdpo.techservice.dto.BookingRequestDTO;
import com.cdpo.techservice.dto.BookingResponseDTO;
import com.cdpo.techservice.dto.BookingStateDTO;
import com.cdpo.techservice.dto.ServiceResponseDTO;
import com.cdpo.techservice.model.Booking;
import com.cdpo.techservice.model.BookingState;
import com.cdpo.techservice.model.Service;
import com.cdpo.techservice.repository.IServiceRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@org.springframework.stereotype.Service
@RequiredArgsConstructor
public class BookingMapper {
    private final IServiceRepository serviceRepository;

    public Booking toEntity(BookingRequestDTO bookingDto) {
        List<Service> services = serviceRepository.findAllById(bookingDto.serviceIds());
        Booking booking = new Booking();
        booking.setServices(services);
        booking.setState(BookingState.NEW);
        booking.setAppointmentTime(booking.getAppointmentTime());
        return booking;
    }

    public BookingResponseDTO toDto(Booking booking) {
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
