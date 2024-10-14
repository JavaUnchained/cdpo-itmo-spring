package com.cdpo.techservice.repository;

import com.cdpo.techservice.model.Booking;
import com.cdpo.techservice.model.BookingState;
import com.cdpo.techservice.model.Service;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;


@Primary
@Repository
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
@RequiredArgsConstructor
public class ServiceBookingSimpleRepository implements IServiceBookingRepository {
    private final List<Booking> bookings = new ArrayList<>();
    private final AtomicLong idGenerator = new AtomicLong();

    private final IServiceRepository iServiceRepository;

    @Override
    public long createBooking(@NotNull @NotEmpty List<Long> serviceId, LocalDateTime appointmentTime) {
        List<Service> services = iServiceRepository.getServicesByIds(serviceId);
        if(services.isEmpty()) return -1;
        long id = idGenerator.incrementAndGet();
        bookings.add(new Booking(id, services, appointmentTime, 0, BookingState.NEW));
        return id;
    }

    @Override
    public boolean setCanceledStatusById(Long id) {
        Optional<Booking> booking = bookings.stream().filter(b -> b.getId() == id).findFirst();
        if (booking.isPresent()) {
            booking.get().setState(BookingState.CANCELLED);
            return true;
        }
        return false;
    }

    @Override
    public Booking updateServiceTimeById(Long id, LocalDateTime appointmentTime) {
        Booking booking = getBookingById(id);
        if (booking != null) {
            if (appointmentTime != null) {
                booking.setAppointmentTime(appointmentTime);
            }
        }
        return booking;

    }

    @Override
    public Booking updateServiceDicountById(Long id, double discount) {
        Booking booking = getBookingById(id);
        if (booking != null) {
            booking.setDiscountPercent(discount);
        }
        return booking;
    }

    @Override
    public List<Booking> getAllBooking() {
        return new ArrayList<>(bookings);
    }

    @Override
    public Booking getBookingById(Long id) {
        return bookings.stream().filter(b -> b.getId() == id).findFirst().orElse(null);
    }
}
