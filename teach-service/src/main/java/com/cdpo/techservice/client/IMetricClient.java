package com.cdpo.techservice.client;

import com.cdpo.techservice.dto.BookingMetricRequestDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IMetricClient {
    ResponseEntity<List<Long>> saveCompletedBookings(List<BookingMetricRequestDTO> completedBookings);
}
