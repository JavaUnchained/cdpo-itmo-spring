package com.cdpo.techservice.service;

import com.cdpo.techservice.dto.*;
import com.cdpo.techservice.exception.NotFoundException;
import com.cdpo.techservice.mapper.BookingMapper;
import com.cdpo.techservice.mapper.RevenueMapper;
import com.cdpo.techservice.model.Booking;
import com.cdpo.techservice.model.BookingState;
import com.cdpo.techservice.repository.IServiceBookingRepository;
import com.cdpo.techservice.repository.IServiceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Primary
@Service
@RequiredArgsConstructor
public class ServiceBookingService implements IServiceBookingService {
    private final IServiceBookingRepository bookingRepository;
    private final IServiceRepository serviceRepository;
    private final BookingMapper bookingMapper;
    private final RevenueMapper revenueMapper;

    @Override
    public long createBooking(BookingRequestDTO requestBooking) {
        List<com.cdpo.techservice.model.Service> services = serviceRepository.findAllById(requestBooking.serviceIds());
        if(services.isEmpty()) {
            throw new NotFoundException("No services found");
        }

        Booking entity = bookingMapper.toEntity(requestBooking);
        entity.setServices(services);
        entity.setState(BookingState.NEW);
        return bookingRepository.save(entity).getId();
    }

    @Override
    public Optional<BookingResponseDTO> getBookingById(Long id) {
        return bookingRepository.findById(id)
                .map(bookingMapper::toDto);
    }

    @Override
    public List<BookingResponseDTO> getAllFilteredBy(BookingStateDTO stateDTO) {
        return bookingRepository
                .findByState(BookingState.valueOf(stateDTO.name()))
                .stream().map(bookingMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<BookingResponseDTO> getAllFilteredBy(LocalDateTime time) {
        return bookingRepository.findByAppointmentTime(time)
                .stream()
                .map(bookingMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<BookingResponseDTO> getAllBookings() {
        return bookingRepository.findAll().stream().map(bookingMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public boolean cancelBooking(Long id) {
        return bookingRepository.updateStateById(BookingState.CANCELLED, id) > 0;
    }

    @Override
    public Optional<BookingResponseDTO> updateBooking(Long id, BookingUpdateTimeDTO updateDTO) {
        bookingRepository.updateAppointmentTimeById(updateDTO.appointmentTime(), id);
        return getBookingById(id);
    }

    @Override
    public Optional<BookingResponseDTO> updateBooking(Long id, BookingUpdateDiscountDTO updateDTO) {
        bookingRepository.updateDiscountPercentById(updateDTO.discountPercent(), id);
        return getBookingById(id);
    }

    @Override
    public List<BookingResponseDTO> getAllProvidedBookings() {
        /**
         * Сейчас не предусмотрена смены статуса на DONE, поэтому сейчас наглядней по времени
         */
        return bookingRepository.findByAppointmentTimeLessThan(LocalDateTime.now()).stream().map(bookingMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public List<RevenueDTO> calculateRevenue(LocalDate from, LocalDate to) {
        List<Booking> bookings;
        if(from == null && to == null) {
            throw new RuntimeException();
        }
        bookings = getBookingList(from, to);

        if (bookings.isEmpty()) {
            throw new NotFoundException("No bookings found");
        }

        return calculateRevenus(bookings);
    }

    private List<Booking> getBookingList(LocalDate from, LocalDate to) {
        List<Booking> bookings;
        if (from == null) {
            bookings = bookingRepository.findByAppointmentTimeLessThan(toLocalDateTime(to));
        } else if (to == null) {
            bookings = bookingRepository.findByAppointmentTimeGreaterThan(toLocalDateTime(from));
        } else {
            bookings = bookingRepository.findByAppointmentTimeBetweenOrderByAppointmentTime(toLocalDateTime(from), toLocalDateTime(to));
        }
        return bookings;
    }

    private LocalDateTime toLocalDateTime(LocalDate localDate) {
        return localDate == null ? null : localDate.atStartOfDay();
    }

    private List<RevenueDTO> calculateRevenus(List<Booking> bookings) {
        List<RevenueDTO> result = new ArrayList<>();
        for (Map.Entry<LocalDate, List<Booking>> entry : groupByDateEntries(bookings)) {
            result.add(revenueMapper.toDTO(entry.getKey(), getDateRevenue(entry)));
        }
        return result;
    }

    private static double getDateRevenue(Map.Entry<LocalDate, List<Booking>> entry) {
        double revenue = 0;
        for (Booking booking : entry.getValue()) {
            revenue += getSumOfPriceWithDiscount(booking);
        }
        return revenue;
    }

    private static double getSumOfPriceWithDiscount(Booking booking) {
        double sum = booking.getServices().stream()
                .map(com.cdpo.techservice.model.Service::getPrice).mapToDouble(v -> v).sum();
        if (booking.getDiscountPercent() != null) {
            sum *= (1 - booking.getDiscountPercent() / 100);
        }
        return sum;
    }

    private static Set<Map.Entry<LocalDate, List<Booking>>> groupByDateEntries(List<Booking> bookings) {
        return bookings.stream().collect(Collectors.groupingBy(booking -> booking.getAppointmentTime().toLocalDate())).entrySet();
    }
}
