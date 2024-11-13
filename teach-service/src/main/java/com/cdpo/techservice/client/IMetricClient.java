package com.cdpo.techservice.client;

import com.cdpo.techservice.dto.BookingMetricRequestDTO;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Validated
public interface IMetricClient {

    @NotEmpty
    @Retryable(retryFor = ConstraintViolationException.class, backoff = @Backoff(delay = 500))
    List<Long> saveCompletedBookings(@Valid List<BookingMetricRequestDTO> completedBookings);
}
