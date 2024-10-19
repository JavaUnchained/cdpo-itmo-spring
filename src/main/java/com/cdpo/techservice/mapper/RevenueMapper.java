package com.cdpo.techservice.mapper;

import com.cdpo.techservice.dto.RevenueDTO;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDate;

@Validated
@Service
public class RevenueMapper {

    public @Valid RevenueDTO toDTO(LocalDate date, double revenue) {
        return new RevenueDTO(date, revenue);
    }

}

