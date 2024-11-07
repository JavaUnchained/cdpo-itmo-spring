package com.cdpo.techservice.dto;

import com.cdpo.techservice.model.ServiceUser;
import jakarta.validation.constraints.*;

import java.io.Serializable;

/**
 * DTO for {@link ServiceUser}
 */
public record ServiceUserDto(@NotNull @NotBlank @Pattern(regexp = "^[A-Z][a-z]+$") String username,
                            @NotNull @NotBlank @Pattern(regexp = "^[A-Z][a-z]+$") String firstName,
                             @NotNull @NotBlank @Pattern(regexp = "^[A-Z][a-z]+$") String lastName,
                             @NotNull @Email @NotBlank String email,
                             @NotNull @NotBlank @Size(min = 8) String password) implements Serializable {
}