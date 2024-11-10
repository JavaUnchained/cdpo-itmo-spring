package com.cdpo.dwh.controller;

import com.cdpo.dwh.dto.BookingResponseDTO;
import com.cdpo.dwh.service.IMetricService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/dwh/metrics")
public class MetricsController {
    private final IMetricService metricsService;

    @PostMapping
    public ResponseEntity<List<Long>> saveCompletedBookings(@Valid List<BookingResponseDTO> completedBookings) {
        return ResponseEntity.ok(metricsService.saveBookings(completedBookings));
    }

}
