package com.cdpo.techservice.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Service {
    private long id;
    private String name;
    private String description;

    public Service(String name, String description) {
        this.name = name;
        this.description = description;
    }
}
