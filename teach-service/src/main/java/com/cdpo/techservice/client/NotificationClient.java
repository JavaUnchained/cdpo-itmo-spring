package com.cdpo.techservice.client;

import com.cdpo.techservice.dto.NotificationBookingDTO;
import com.cdpo.techservice.dto.NotificationDiscountDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "notification-service", url = "http://localhost:8009/api/v1/notification")
public interface NotificationClient {

    @PostMapping("/discount")
    ResponseEntity<Void> sendDiscountNotification(@RequestBody NotificationDiscountDTO notification);

    @PostMapping("/booking")
    ResponseEntity<Void> sendBookingNotification(@RequestBody NotificationBookingDTO notification);
}
