package com.cdpo.techservice.service;

import com.cdpo.techservice.dto.*;
import com.cdpo.techservice.exception.IllegalInputParameters;
import com.cdpo.techservice.exception.NotFoundException;
import com.cdpo.techservice.mapper.BookingMapper;
import com.cdpo.techservice.mapper.RevenueMapper;
import com.cdpo.techservice.model.Booking;
import com.cdpo.techservice.model.BookingState;
import com.cdpo.techservice.repository.IServiceBookingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Primary
@Service
@RequiredArgsConstructor
public class ServiceBookingService implements IServiceBookingService {
    private static final String NOT_FOUND = "Booking not found";
    private static final String BOTH_CANNOT_BE_NULL = "From and To both cannot be null";

    private final IServiceBookingRepository bookingRepository;
    private final BookingMapper bookingMapper;
    private final RevenueMapper revenueMapper;

    @Override
    public long createBooking(BookingRequestDTO requestBooking) {
        return bookingRepository.save(bookingMapper.toEntity(requestBooking)).getId();
    }

    @Override
    public BookingResponseDTO getBookingById(Long id) {
        return bookingRepository.findById(id)
                .map(bookingMapper::toDto).orElseThrow(() -> new NotFoundException(NOT_FOUND));
    }

    @Override
    public List<BookingResponseDTO> getAllFilteredBy(BookingStateDTO stateDTO) {
        List<Booking> bookings = bookingRepository.findByState(BookingState.valueOf(stateDTO.name()));
        if(bookings.isEmpty()) throw new NotFoundException(NOT_FOUND);
        return bookings.stream().map(bookingMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public List<BookingResponseDTO> getAllFilteredBy(LocalDateTime time) {
        List<Booking> bookings = bookingRepository.findByAppointmentTime(time);
        if(bookings.isEmpty()) throw new NotFoundException(NOT_FOUND);
        return bookings.stream().map(bookingMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public List<BookingResponseDTO> getAllBookings() {
        List<Booking> bookings = bookingRepository.findAll();
        if(bookings.isEmpty()) throw new NotFoundException(NOT_FOUND);
        return bookings.stream().map(bookingMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public void cancelBooking(Long id) {
        if (bookingRepository.updateStateById(BookingState.CANCELLED, id) == 0) {
            throw new NotFoundException(NOT_FOUND);
        }
    }

    @Override
    public BookingResponseDTO updateBooking(Long id, BookingUpdateTimeDTO updateDTO) {
        bookingRepository.updateAppointmentTimeById(updateDTO.appointmentTime(), id);
        return getBookingById(id);
    }

    @Override
    public BookingResponseDTO updateBooking(Long id, BookingUpdateDiscountDTO updateDTO) {
        bookingRepository.updateDiscountPercentById(updateDTO.discountPercent(), id);
        return getBookingById(id);
    }

    @Override
    public List<BookingResponseDTO> getAllProvidedBookings() {
        /* Пока статуса DONE нет, поэтому сейчас наглядней по времени */
        List<Booking> bookings = bookingRepository.findByAppointmentTimeLessThan(LocalDateTime.now());
        if(bookings.isEmpty()) throw new NotFoundException(NOT_FOUND);
        return bookings.stream().map(bookingMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public List<RevenueDTO> calculateRevenue(LocalDate from, LocalDate to) {
        if(from == null && to == null) throw new IllegalInputParameters(BOTH_CANNOT_BE_NULL);
        List<Booking> bookings = getBookingList(from, to);
        if (bookings.isEmpty()) throw new NotFoundException(NOT_FOUND);
        return calculateRevenues(bookings);
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

    private List<RevenueDTO> calculateRevenues(List<Booking> bookings) {
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
