package com.cdpo.notifier.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class NotificationDTO {
    private String email;
    private String firstName;
    private String lastName;

    public String getEmail() {
        return email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLstName() {
        return lastName;
    }
}
