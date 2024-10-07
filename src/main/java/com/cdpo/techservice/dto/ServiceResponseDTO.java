package com.cdpo.techservice.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ServiceResponseDTO {
    private long id;
    private String name;
    private String description;

    public ServiceResponseDTO(long id, ServiceRequestDTO service) {
        this.id = id;
        this.name = service.getName();
        this.description = service.getDescription();
    }
}
