package com.cdpo.techservice.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.List;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record BookingRequestDTO(
        @NotNull
        @NotEmpty
        List<Long> serviceIds,
        @NotNull
        @Future
        LocalDateTime  appointmentTime
) {
}
