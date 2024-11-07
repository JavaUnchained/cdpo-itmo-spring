package com.cdpo.techservice.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
public class NotificationDiscountDTO extends NotificationDTO {
    @NotNull
    private final Double discountInPercent;

    public NotificationDiscountDTO(Long id, String email, String firstName, String lastName, Double discountInPercent) {
        super(id, email, firstName, lastName);
        this.discountInPercent = discountInPercent;
    }
}
