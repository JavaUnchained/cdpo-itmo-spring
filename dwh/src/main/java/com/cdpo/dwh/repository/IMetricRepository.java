package com.cdpo.dwh.repository;

import com.cdpo.dwh.dto.BookingResponseDTO;

import java.util.List;

public interface IMetricRepository {
    List<Long> save(List<BookingResponseDTO> bookings);
}
