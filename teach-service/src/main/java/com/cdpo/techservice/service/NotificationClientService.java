package com.cdpo.techservice.service;

import com.cdpo.techservice.client.NotificationClient;
import com.cdpo.techservice.dto.NotificationBookingDTO;
import com.cdpo.techservice.dto.NotificationDiscountDTO;
import com.cdpo.techservice.exception.NotificationServiceException;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
@Service
@RequiredArgsConstructor
@ConditionalOnProperty(name = "notification.enable", havingValue = "true")
public class NotificationClientService implements INotificationClientService {
    private final NotificationClient notificationClient;

    @Override
    public void sendNotification(NotificationBookingDTO notification) {
        if (!notificationClient.sendBookingNotification(notification).getStatusCode().is2xxSuccessful()) {
            throw new NotificationServiceException();
        }
    }

    @Override
    public void sendNotification(NotificationDiscountDTO notification) {
        if (!notificationClient.sendDiscountNotification(notification).getStatusCode().is2xxSuccessful()) {
            throw new NotificationServiceException();
        }
    }

}