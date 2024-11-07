package com.cdpo.techservice.service;

import com.cdpo.techservice.dto.NotificationBookingDTO;
import com.cdpo.techservice.dto.NotificationDiscountDTO;

public interface INotificationClientService {
    void sendNotification(NotificationBookingDTO notification);
    void sendNotification(NotificationDiscountDTO notification);
}
