package com.cdpo.notifier.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NotificationDiscountDTO extends NotificationDTO {
    private Double discountInPercent;
}
