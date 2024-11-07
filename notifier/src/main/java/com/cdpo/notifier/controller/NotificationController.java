package com.cdpo.notifier.controller;

import com.cdpo.notifier.dto.NotificationBookingDTO;
import com.cdpo.notifier.dto.NotificationDiscountDTO;
import com.cdpo.notifier.service.ISenderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/notification")
public class NotificationController {
    private final ISenderService senderService;

    @PostMapping("/booking")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Void> sendBookingState(@RequestBody NotificationBookingDTO bookingDTO){
        return senderService.send(bookingDTO);
    }

    @PostMapping("/booking")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Void> sendDiscount(@RequestBody NotificationDiscountDTO discountDTO){
        return senderService.send(discountDTO);
    }
}
