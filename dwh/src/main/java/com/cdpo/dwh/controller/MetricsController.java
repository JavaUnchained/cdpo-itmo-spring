package com.cdpo.dwh.controller;

import com.cdpo.dwh.dto.BookingResponseDTO;
import com.cdpo.dwh.service.IMetricService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/dwh/metric")
@Slf4j
public class MetricsController {
    private final IMetricService metricsService;

    @PostMapping
    public ResponseEntity<List<Long>> saveCompletedBookings(@RequestBody  @Valid List<BookingResponseDTO> completedBookings) {
        log.info("receive query to save metrics");
        List<Long> ids = metricsService.saveBookings(completedBookings);
        log.info("ids: {}", ids);
        return ResponseEntity.ok(ids);
    }

}
