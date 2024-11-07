package com.cdpo.notifier.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum BookingStateDTO {
    NEW("Created"), DONE("Done"), CANCELLED("Cancelled");

    private final String stateStr;
}
