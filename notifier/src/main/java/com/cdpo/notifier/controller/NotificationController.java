package com.cdpo.notifier.controller;

import com.cdpo.notifier.dto.NotificationBookingDTO;
import com.cdpo.notifier.dto.NotificationDiscountDTO;
import com.cdpo.notifier.service.ISenderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/notification")
@Slf4j
@Validated
public class NotificationController {
    private final ISenderService senderService;

    @PostMapping("/booking")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Void> sendBookingState(@RequestBody @Valid NotificationBookingDTO bookingDTO){;
        log.debug("BookingDTO: {}", bookingDTO);
        return senderService.send(bookingDTO);
    }

    @PostMapping("/discount")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Void> sendDiscount(@RequestBody @Valid NotificationDiscountDTO discountDTO){
        log.debug("DiscountDTO: {}", discountDTO);
        return senderService.send(discountDTO);
    }
}
