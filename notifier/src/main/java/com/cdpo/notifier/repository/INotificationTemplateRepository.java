package com.cdpo.notifier.repository;

import com.cdpo.notifier.dto.BookingStateDTO;
import reactor.core.publisher.Mono;

public interface INotificationTemplateRepository {
    Mono<String> getBookingTemplate(BookingStateDTO bookingState, String firstName, String lastName);

    Mono<String> getDiscountTemplate(Double discountInPercent, String firstName, String lastName);
}
