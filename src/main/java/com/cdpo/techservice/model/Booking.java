package com.cdpo.techservice.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class Booking {
    private long id;
    private Service service;
    private LocalDateTime appointmentTime;
}


