package com.cdpo.notifier.repository;

import com.cdpo.notifier.dto.BookingStateDTO;

public interface INotificationTemplateRepository {
    String getBookingTemplate(BookingStateDTO bookingState, String firstName, String lastName);

    String getDiscountTemplate(Double discountInPercent, String firstName, String lastName);
}
