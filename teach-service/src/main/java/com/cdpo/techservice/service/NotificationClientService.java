package com.cdpo.techservice.service;

import com.cdpo.techservice.client.NotificationClient;
import com.cdpo.techservice.dto.NotificationBookingDTO;
import com.cdpo.techservice.dto.NotificationDiscountDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.HttpStatusCode;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
@Slf4j
@RequiredArgsConstructor
@ConditionalOnProperty(name = "notification.enable", havingValue = "true")
public class NotificationClientService implements INotificationClientService {
    public static final String NOTIFICATION_SERVICE_EXCEPTION = "Notification Service Exception. Http code {}";
    private final NotificationClient notificationClient;

    @Override
    @Async
    public void sendNotification(NotificationBookingDTO notification) {
        CompletableFuture.runAsync(() ->{
            HttpStatusCode statusCode = notificationClient.sendBookingNotification(notification).getStatusCode();
            if (statusCode.is2xxSuccessful()) {
              log.error(NOTIFICATION_SERVICE_EXCEPTION, statusCode);
            }
        });
    }

    @Override
    @Async
    public void sendNotification(NotificationDiscountDTO notification) {
        CompletableFuture.runAsync(() ->{
            HttpStatusCode statusCode = notificationClient.sendDiscountNotification(notification).getStatusCode();
            if (!statusCode.is2xxSuccessful()) {
                log.error(NOTIFICATION_SERVICE_EXCEPTION, statusCode);
            }
        });

    }

}