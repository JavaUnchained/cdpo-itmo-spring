package com.cdpo.techservice.dto;

import com.cdpo.techservice.model.ServiceUser;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.*;

import java.io.Serializable;

/**
 * DTO for {@link ServiceUser}
 */
public record ServiceUserUpdateDto(@Nullable @NotBlank @Pattern(regexp = "^[A-Z][a-z]+$") String username,
                                   @Nullable @NotBlank @Pattern(regexp = "^[A-Z][a-z]+$") String firstName,
                                   @Nullable @NotBlank @Pattern(regexp = "^[A-Z][a-z]+$") String lastName,
                                   @Nullable @Email @NotBlank String email,
                                   @Nullable @NotBlank @Size(min = 8) String password) implements Serializable {
}