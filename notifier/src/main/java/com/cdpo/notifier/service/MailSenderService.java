package com.cdpo.notifier.service;

import com.cdpo.notifier.dto.NotificationBookingDTO;
import com.cdpo.notifier.dto.NotificationDiscountDTO;
import com.cdpo.notifier.repository.INotificationTemplateRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Service
@RequiredArgsConstructor
public class MailSenderService implements ISenderService {
    public static final String BOOKING_SUBJECT = "Изменён статус заказа №%d";
    public static final String DISCOUNT_SUBJ = "Заказ №%d отменён. Просим извенения и предлагаем скидку";
    private final JavaMailSender javaMailSender;
    private final INotificationTemplateRepository notificationTemplateRepository;

    @Value("${spring.mail.username}")
    private String adminEmail;

    @Override
    public Mono<Void> send(NotificationBookingDTO notification) {
        return notificationTemplateRepository.getBookingTemplate(
                        notification.getBookingState(),
                        notification.getFirstName(),
                        notification.getLastName()
                ).log().flatMap(message -> getMimeMessage(
                        message,
                        BOOKING_SUBJECT,
                        notification.getId(),
                        notification.getEmail())
                ).flatMap(this::sendSimpleMessage)
                .subscribeOn(Schedulers.boundedElastic()).then();
    }

    @Override
    public Mono<Void> send(NotificationDiscountDTO notification) {
        return notificationTemplateRepository.getDiscountTemplate(
                        notification.getDiscountInPercent(),
                        notification.getFirstName(),
                        notification.getLastName()
                ).log().flatMap(message -> getMimeMessage(
                        message,
                        DISCOUNT_SUBJ,
                        notification.getId(),
                        notification.getEmail())
                ).flatMap(this::sendSimpleMessage)
                .subscribeOn(Schedulers.boundedElastic()).then();
    }

    private Mono<Object> sendSimpleMessage(MimeMessage message) {
        javaMailSender.send(message);
        return Mono.empty();
    }

    private Mono<SimpleMailMessage> getSimpleMessage(String message, String subject, String... to) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom(adminEmail);
        mailMessage.setTo(to);
        mailMessage.setSubject(subject);
        mailMessage.setText(message);
        return Mono.just(mailMessage);
    }

    private Mono<MimeMessage> getMimeMessage(String messageStr, String subject, Long id, String... to) {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(adminEmail);
            helper.setTo(to);
            helper.setSubject(String.format(subject, id));
            helper.setText(messageStr, true);
            return Mono.just(message);
        } catch (MessagingException e) {
            throw new RuntimeException("Failed to send email", e);
        }
    }
}
