package com.cdpo.techservice.controller;

import com.cdpo.techservice.dto.*;
import com.cdpo.techservice.service.IServiceBookingService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Validated
@RestController
@RequestMapping("/api/v1/services/booking")
@AllArgsConstructor
public class ServiceBookingController {
    private final IServiceBookingService iService;


    @PostMapping
    public ResponseEntity<Long> createBooking(@RequestBody @Valid  BookingRequestDTO requestBooking) {
        long createdId = iService.createBooking(requestBooking);
        return createdId == -1 ? ResponseEntity.badRequest().build() : new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @DeleteMapping
    public ResponseEntity<Void> cancelBooking(@RequestParam @Positive Long id) {
        return iService.cancelBooking(id) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    @GetMapping
    public ResponseEntity<List<BookingResponseDTO>> getBookingsByTime(@RequestParam("target_time") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime targetTime) {
        return ResponseEntity.ok(iService.getAllFilteredBy(targetTime));
    }

    @GetMapping("/all")
    public ResponseEntity<List<BookingResponseDTO>> getBookings(@RequestParam(required = false) BookingStateDTO stateDTO) {
        return ResponseEntity.ok(stateDTO != null ? iService.getAllFilteredBy(stateDTO) : iService.getAllBookings());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookingResponseDTO> getBooking(@PathVariable long id,
                                                          @RequestParam(required = false) BookingStateDTO stateDTO) {
        return iService.getBookingById(id)
                        .map(ResponseEntity::ok)
                        .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookingResponseDTO> changeBookingAppointmentTime(@PathVariable @Positive long id,
                                                                           @Valid @RequestBody BookingUpdateTimeDTO updateDTO) {
        return iService.updateBooking(id, updateDTO)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}/discount")
    public ResponseEntity<BookingResponseDTO> changeDiscount(@PathVariable @Positive long id,
                                                             @Valid @RequestBody BookingUpdateDiscountDTO updateDTO) {
        return iService.updateBooking(id, updateDTO)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/done")
    public ResponseEntity<List<BookingResponseDTO>> getProvidedService() {
        List<BookingResponseDTO> providedBookings = iService.getAllProvidedBookings();
        return providedBookings.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(providedBookings);
    }
}
