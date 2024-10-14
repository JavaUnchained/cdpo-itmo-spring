package com.cdpo.techservice.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class Booking {
    private long id;
    private List<Service> services;
    private LocalDateTime appointmentTime;
    private double discountPercent;
    private BookingState state;
}


