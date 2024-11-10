package com.cdpo.dwh.service;

import com.cdpo.dwh.dto.BookingResponseDTO;

import java.util.List;

public interface IMetricService {
    List<Long> saveBookings(List<BookingResponseDTO> bookingAgregateDTO);
}
