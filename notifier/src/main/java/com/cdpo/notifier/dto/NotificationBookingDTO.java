package com.cdpo.notifier.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NotificationBookingDTO extends NotificationDTO{
    private BookingStateDTO bookingState;
}
