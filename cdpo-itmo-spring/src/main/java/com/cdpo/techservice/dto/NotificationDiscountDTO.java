package com.cdpo.techservice.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NotificationDiscountDTO extends NotificationDTO {
    @NotNull
    private Double discountInPercent;
}
