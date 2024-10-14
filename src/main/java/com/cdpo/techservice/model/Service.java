package com.cdpo.techservice.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Service {
    private long id;
    private String name;
    private String description;
    private long duration;
    private double price;
}
