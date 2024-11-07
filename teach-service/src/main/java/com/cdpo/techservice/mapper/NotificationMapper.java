package com.cdpo.techservice.mapper;

import com.cdpo.techservice.dto.BookingStateDTO;
import com.cdpo.techservice.dto.NotificationBookingDTO;
import com.cdpo.techservice.dto.NotificationDiscountDTO;
import com.cdpo.techservice.model.Booking;
import com.cdpo.techservice.model.ServiceUser;
import org.springframework.stereotype.Service;

@Service
public class NotificationMapper {

    public NotificationBookingDTO toBookingNotification(Booking booking) {
        ServiceUser user = booking.getUser();
        return new NotificationBookingDTO(
                booking.getId(),
                user.getEmail(),
                user.getFirstName(),
                user.getLastName(),
                BookingStateDTO.valueOf(booking.getState().name())
        );
    }

    public NotificationDiscountDTO toDiscountNotification(Booking booking) {
        ServiceUser user = booking.getUser();
        return new NotificationDiscountDTO(
                booking.getId(),
                user.getEmail(),
                user.getFirstName(),
                user.getLastName(),
                booking.getDiscountPercent()
        );
    }
}
