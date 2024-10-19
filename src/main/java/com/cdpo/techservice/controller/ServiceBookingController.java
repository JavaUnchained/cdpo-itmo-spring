package com.cdpo.techservice.controller;

import com.cdpo.techservice.dto.*;
import com.cdpo.techservice.service.IServiceBookingService;
import jakarta.annotation.Nullable;
import jakarta.validation.Valid;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
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
        return new ResponseEntity<>(iService.createBooking(requestBooking), HttpStatus.CREATED);
    }

    @DeleteMapping
    public ResponseEntity<Void> cancelBooking(@RequestParam @Positive Long id) {
        iService.cancelBooking(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<BookingResponseDTO>> getBookingsByTime(@RequestParam("target_time")
                                                                      @PastOrPresent
                                                                      @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                                                                      LocalDateTime targetTime) {
        return ResponseEntity.ok(iService.getAllFilteredBy(targetTime));
    }

    @GetMapping("/all")
    public ResponseEntity<List<BookingResponseDTO>> getBookings(@RequestParam(required = false) BookingStateDTO stateDTO) {
        return ResponseEntity.ok(stateDTO != null ? iService.getAllFilteredBy(stateDTO) : iService.getAllBookings());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookingResponseDTO> getBooking(@PathVariable long id) {
        return ResponseEntity.ok(iService.getBookingById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookingResponseDTO> changeBookingAppointmentTime(@PathVariable @Positive long id,
                                                                           @Valid @RequestBody BookingUpdateTimeDTO updateDTO) {
        return ResponseEntity.ok(iService.updateBooking(id, updateDTO));
    }

    @PutMapping("/{id}/discount")
    public ResponseEntity<BookingResponseDTO> changeDiscount(@PathVariable @Positive long id,
                                                             @Valid @RequestBody BookingUpdateDiscountDTO updateDTO) {
        return ResponseEntity.ok(iService.updateBooking(id, updateDTO));
    }

    @GetMapping("/done")
    public ResponseEntity<List<BookingResponseDTO>> getProvidedService() {
        List<BookingResponseDTO> providedBookings = iService.getAllProvidedBookings();
        return ResponseEntity.ok(providedBookings);
    }

    @GetMapping("/revenue")
    public ResponseEntity<List<RevenueDTO>> getBookingRevenue(
                                                        @RequestParam(value = "from", required = false)
                                                        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                                                        @Nullable @PastOrPresent LocalDate from,
                                                        @RequestParam(value = "to", required = false)
                                                        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                                                        LocalDate to) {
        return ResponseEntity.ok(iService.calculateRevenue(from, to));
    }
}
