package com.cdpo.techservice.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
public class NotificationBookingDTO extends NotificationDTO {
    @NotNull
    private final BookingStateDTO bookingState;

    public NotificationBookingDTO(Long id, String email, String firstName, String lastName, BookingStateDTO bookingState) {
        super(id, email, firstName, lastName);
        this.bookingState = bookingState;
    }
}
