package com.cdpo.notifier.service;

import com.cdpo.notifier.dto.NotificationBookingDTO;
import com.cdpo.notifier.dto.NotificationDiscountDTO;
import com.cdpo.notifier.repository.INotificationTemplateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class MailSenderService implements ISenderService{
    private final JavaMailSender javaMailSender;
    private final INotificationTemplateRepository notificationTemplateRepository;

    @Value("${spring.mail.username}")
    private String adminEmail;


    @Override
    public Mono<Void> send(NotificationBookingDTO notification) {
        String email = notification.getEmail();
        String message = notificationTemplateRepository.getBookingTemplate(notification.getBookingState(), notification.getFirstName(), notification.getLastName());
        String subject = "Booking status changed.";
        javaMailSender.send(getSimpleMessage(message, subject, email));
        return Mono.empty();
    }

    @Override
    public Mono<Void> send(NotificationDiscountDTO notification) {
        String email = notification.getEmail();
        String message = notificationTemplateRepository.getDiscountTemplate(notification.getDiscontInPercent(),notification.getFirstName(), notification.getLastName());
        String subject = "Booking status changed.";
        javaMailSender.send(getSimpleMessage(message, subject, email));
        return Mono.empty();
    }

    private SimpleMailMessage getSimpleMessage(String message, String subject, String... setTo){
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom(adminEmail);
        mailMessage.setTo(setTo);
        mailMessage.setSubject(subject);
        mailMessage.setText(message);
        return mailMessage;
    }
}
