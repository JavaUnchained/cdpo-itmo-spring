package com.cdpo.notifier.repository;

import com.cdpo.notifier.dto.BookingStateDTO;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public class NotificationTemplateLocalRepository implements INotificationTemplateRepository {
    private static final String BOOKING_TEMPLATE = """
            <!DOCTYPE html>
            <html lang="en">
            <head>
                <meta charset="UTF-8">
                <meta name="viewport" content="width=device-width, initial-scale=1.0">
                <title>Изменение статуса бронирования</title>
                <style>
                    body {
                        font-family: Arial, sans-serif;
                        background-color: #f4f4f4;
                        margin: 0;
                        padding: 20px;
                    }
                    .container {
                        max-width: 600px;
                        margin: auto;
                        background: white;
                        padding: 20px;
                        border-radius: 8px;
                        box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
                    }
                    h1 {
                        color: #333;
                    }
                    p {
                        font-size: 16px;
                        line-height: 1.5;
                        color: #555;
                    }
                    .button {
                        display: inline-block;
                        margin-top: 20px;
                        padding: 10px 15px;
                        background-color: #007BFF;
                        color: white;
                        text-decoration: none;
                        border-radius: 5px;
                    }
                    .footer {
                        margin-top: 20px;
                        font-size: 12px;
                        color: #aaa;
                    }
                </style>
            </head>
            <body>
                        
            <div class="container">
                <h1>Изменение статуса бронирования</h1>
                <p>Здравствуйте, %s %s!</p>
                <p>Мы хотим сообщить вам, что статус вашего бронирования изменился на:</p>
                <h2>%s</h2>
                <p>Если у вас есть какие-либо вопросы или вам нужна дополнительная информация, не стесняйтесь обращаться к нам.</p>
                <div class="footer">
                    <p>Спасибо за использование наших услуг!</p>
                    <p>Команда поддержки</p>
                </div>
            </div>         
            </body>
            </html>
            """;

    private static final String DISCOUNT_TEMPLATE = """
            <!DOCTYPE html>
            <html lang="en">
            <head>
                <meta charset="UTF-8">
                <meta name="viewport" content="width=device-width, initial-scale=1.0">
                <title>Отмена заказа и предоставление скидки</title>
                <style>
                    body {
                        font-family: Arial, sans-serif;
                        background-color: #f4f4f4;
                        margin: 0;
                        padding: 20px;
                    }
                    .container {
                        max-width: 600px;
                        margin: auto;
                        background: white;
                        padding: 20px;
                        border-radius: 8px;
                        box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
                    }
                    h1 {
                        color: #333;
                    }
                    p {
                        font-size: 16px;
                        line-height: 1.5;
                        color: #555;
                    }
                    .button {
                        display: inline-block;
                        margin-top: 20px;
                        padding: 10px 15px;
                        background-color: #007BFF;
                        color: white;
                        text-decoration: none;
                        border-radius: 5px;
                    }
                    .footer {
                        margin-top: 20px;
                        font-size: 12px;
                        color: #aaa;
                    }
                </style>
            </head>
            <body>
                        
            <div class="container">
                <h1>Ваш заказ отменён</h1>
                <p>Здравствуйте, %s %s!</p>
                <p>Мы хотим сообщить вам, что ваш заказ был отменён. Приносим извенения и предоставляем скидку на следующую бронь:</p>
                <h2>Размер вашей скидки на следующий заказ %.2f%%</h2>
                <p>Если у вас есть какие-либо вопросы или вам нужна дополнительная информация, не стесняйтесь обращаться к нам.</p>
                <div class="footer">
                    <p>Спасибо за использование наших услуг!</p>
                    <p>Команда поддержки</p>
                </div>
            </div>         
            </body>
            </html>
            """;


    @Override
    public Mono<String> getBookingTemplate(BookingStateDTO bookingState, String firstName, String lastName) {
        return Mono.just(String.format(BOOKING_TEMPLATE, firstName, lastName, bookingState.getStateStr()));
    }

    @Override
    public Mono<String> getDiscountTemplate(Double discountInPercent, String firstName, String lastName) {
        return Mono.just(String.format(DISCOUNT_TEMPLATE, firstName, lastName, discountInPercent));
    }
}
