package com.cdpo.techservice.repository;

import com.cdpo.techservice.model.Booking;
import com.cdpo.techservice.model.Service;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
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
    public long createBooking(long serviceId, LocalDateTime appointmentTime) {
        Service service = iServiceRepository.getServiceById(serviceId);
        if(service == null) return -1;
        long id = idGenerator.incrementAndGet();
        bookings.add(new Booking(id, service, appointmentTime));
        return id;
    }

    @Override
    public boolean deleteBookingById(Long id) {
        return bookings.removeIf(b -> b.getId() == id);
    }

    @Override
    public Booking updateServiceById(Long id, LocalDateTime appointmentTime) {
        Booking booking = getBookingById(id);
        if (booking != null) {
            if (appointmentTime != null) {
                booking.setAppointmentTime(appointmentTime);
            }
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
