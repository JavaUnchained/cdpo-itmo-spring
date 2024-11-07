package com.cdpo.notifier.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class NotificationDTO {
    private Long id;
    private String email;
    private String firstName;
    private String lastName;
}
