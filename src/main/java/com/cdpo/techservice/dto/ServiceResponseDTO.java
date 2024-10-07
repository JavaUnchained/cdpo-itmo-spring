package com.cdpo.techservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ServiceResponseDTO {
    @NotNull
    private long id;
    @NotNull
    @NotBlank
    private String name;
    private String description;

    public ServiceResponseDTO(long id, ServiceRequestDTO service) {
        this.id = id;
        this.name = service.getName();
        this.description = service.getDescription();
    }
}
