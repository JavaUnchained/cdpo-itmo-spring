package com.cdpo.techservice.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
public abstract class NotificationDTO {
    @NotNull
    @Positive
    private final Long id;
    @NotNull
    @Email(message = "Invalid email")
    private final String email;
    @NotNull
    @NotBlank
    private final String firstName;
    @NotNull
    @NotBlank
    private final String lastName;

    public NotificationDTO(Long id, String email, String firstName, String lastName) {
        this.id = id;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
    }
}
