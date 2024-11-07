package com.cdpo.notifier.service;

import com.cdpo.notifier.dto.NotificationBookingDTO;
import com.cdpo.notifier.dto.NotificationDiscountDTO;
import reactor.core.publisher.Mono;

public interface ISenderService {
    Mono<Void> send(NotificationBookingDTO notification);

    Mono<Void> send(NotificationDiscountDTO notification);
}
