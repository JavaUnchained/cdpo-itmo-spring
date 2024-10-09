package com.cdpo.techservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ServiceResponseDTO(long id, @NotNull @NotBlank String name, String description) {

    public ServiceResponseDTO(long id, ServiceRequestDTO service) {
        this(id, service.name(), service.description());
    }
}
