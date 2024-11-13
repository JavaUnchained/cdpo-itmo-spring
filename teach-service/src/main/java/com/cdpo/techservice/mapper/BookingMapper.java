package com.cdpo.techservice.mapper;

import com.cdpo.techservice.dto.*;
import com.cdpo.techservice.exception.NotFoundException;
import com.cdpo.techservice.model.Booking;
import com.cdpo.techservice.model.BookingState;
import com.cdpo.techservice.model.Service;
import com.cdpo.techservice.model.ServiceUser;
import com.cdpo.techservice.repository.IServiceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@org.springframework.stereotype.Service
@RequiredArgsConstructor
public class BookingMapper {
    public static final String NOT_FOUND = "Services not found";
    private final IServiceRepository serviceRepository;
    private final ServiceMapper serviceMapper;

    public Booking toEntity(BookingRequestDTO bookingDto, ServiceUser user) {
        List<Service> services = serviceRepository.findAllById(bookingDto.serviceIds());
        if (services.isEmpty()) {
            throw new NotFoundException(NOT_FOUND);
        }
        Booking booking = new Booking();
        booking.setServices(services);
        booking.setState(BookingState.NEW);
        booking.setAppointmentTime(booking.getAppointmentTime());
        booking.setUser(user);
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

    public BookingMetricRequestDTO toMetricDto(Booking booking) {
        List<ServiceResponseDTO> services = booking.getServices()
                .stream()
                .map(service -> serviceMapper.toDTOWithDiscount(service, booking.getDiscountPercent()))
                .toList();

        return new BookingMetricRequestDTO(
                booking.getId(), services, booking.getAppointmentTime(), BookingStateDTO.DONE, booking.getUser().getId()
        );
    }


    public Booking merge(Booking booking, BookingDTO updateDTO, ServiceUser user) {
        if (updateDTO.appointmentTime() != null) {
            booking.setAppointmentTime(updateDTO.appointmentTime());
        }
        if(updateDTO.discountPercent() != null) {
            booking.setDiscountPercent(updateDTO.discountPercent());
        }
        if(updateDTO.state() != null) {
            booking.setState(BookingState.valueOf(updateDTO.state().name()));
        }
        if(updateDTO.serviceIds() != null) {
            List<Service> services = serviceRepository.findAllById(updateDTO.serviceIds());
            if(services.isEmpty()) throw new NotFoundException(NOT_FOUND);
            booking.setServices(services);
        }
        if(user != null) {
            booking.setUser(user);
        }
        return booking;
    }
}
