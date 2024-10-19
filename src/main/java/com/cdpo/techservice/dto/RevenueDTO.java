package com.cdpo.techservice.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.PositiveOrZero;

import java.time.LocalDate;

public record RevenueDTO(@NotNull @PastOrPresent LocalDate revenueDate, @PositiveOrZero double revenue) {
}
