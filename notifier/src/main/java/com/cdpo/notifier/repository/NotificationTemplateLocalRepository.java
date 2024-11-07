package com.cdpo.notifier.repository;

import com.cdpo.notifier.dto.BookingStateDTO;

public class NotificationTemplateLocalRepository implements INotificationTemplateRepository {
    @Override
    public String getBookingTemplate(BookingStateDTO bookingState, String firstName, String lastName) {
        return null;
    }

    @Override
    public String getDiscountTemplate(Double discontInPercent, String firstName, String lastName) {
        return null;
    }
}
