package com.cdpo.techservice.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.LocalDateTime;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record BookingResponseDTO (
    @Positive
    long id,

    @NotNull
    ServiceResponseDTO service,

    @NotNull
    LocalDateTime appointmentTime
){

}
