package com.cdpo.techservice.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.cdpo.techservice.model.Privilege.*;

@Getter
@RequiredArgsConstructor
public enum RoleType {
    ROLE_USER(List.of(READ_ANIMAL, WRITE_ANIMAL, EDITE_ANIMAL, READ_BOOKING, WRITE_BOOKING, EDITE_BOOKING)),
    ROLE_OPERATOR(List.of(READ_ANIMAL, WRITE_ANIMAL, EDITE_ANIMAL, READ_BOOKING, WRITE_BOOKING, EDITE_BOOKING, MANAGE_BOOKING_DISCOUNT)),
    ROLE_SUPER_USER(List.of(Privilege.values()));

    private final List<Privilege> privileges;
}
