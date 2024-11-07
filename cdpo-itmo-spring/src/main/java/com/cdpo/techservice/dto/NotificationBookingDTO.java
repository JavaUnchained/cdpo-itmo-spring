package com.cdpo.techservice.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NotificationBookingDTO extends NotificationDTO {
    @NotNull
    private BookingStateDTO bookingState;
}
