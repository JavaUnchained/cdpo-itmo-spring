package com.cdpo.techservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ServiceRequestDTO(@NotNull @NotBlank String name,
                                String description,
                                Long duration,
                                Double price) {
}
