package com.cdpo.techservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record ServiceResponseDTO(long id,
                                 @NotNull @NotBlank String name,
                                 String description,
                                 @Positive long duration,
                                 @Positive double price) {

    public ServiceResponseDTO(long id, ServiceRequestDTO service) {
        this(id, service.name(), service.description(), service.duration(), service.price());
    }
}
