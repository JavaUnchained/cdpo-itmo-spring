package com.cdpo.dwh.service;

import com.cdpo.dwh.dto.BookingResponseDTO;
import com.cdpo.dwh.repository.IMetricRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MetricService implements IMetricService {
    private final IMetricRepository metricRepository;

    @Override
    public List<Long> saveBookings(List<BookingResponseDTO> bookings) {
        return metricRepository.save(bookings);
    }
}
