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
import org.springframework.security.access.annotation.Secured;
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


    @Secured("ROLE_USER")
    @PostMapping
    public ResponseEntity<Long> createBooking(@RequestBody @Valid  BookingRequestDTO requestBooking) {
        return new ResponseEntity<>(iService.createBooking(requestBooking), HttpStatus.CREATED);
    }

    @Secured("ROLE_OPERATOR")
    @DeleteMapping
    public ResponseEntity<Void> cancelBooking(@RequestParam @Positive Long id) {
        iService.cancelBooking(id);
        return ResponseEntity.noContent().build();
    }

    @Secured("ROLE_SUPER_USER")
    @GetMapping
    public ResponseEntity<List<BookingResponseDTO>> getBookingsByTime(@RequestParam("target_time")
                                                                      @PastOrPresent
                                                                      @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                                                                      LocalDateTime targetTime) {
        return ResponseEntity.ok(iService.getAllFilteredBy(targetTime));
    }

    @Secured({"ROLE_USER", "ROLE_OPERATOR"})
    @GetMapping("/all")
    public ResponseEntity<List<BookingResponseDTO>> getBookings(@RequestParam(required = false) BookingStateDTO stateDTO) {
        // todo ROLE_USER видит только свои заказы
        return ResponseEntity.ok(stateDTO != null ? iService.getAllFilteredBy(stateDTO) : iService.getAllBookings());
    }

    @Secured({"ROLE_USER", "ROLE_OPERATOR"})
    @GetMapping("/{id}")
    public ResponseEntity<BookingResponseDTO> getBooking(@PathVariable long id) {
        return ResponseEntity.ok(iService.getBookingById(id));
    }

    @Secured({"ROLE_USER", "ROLE_OPERATOR"})
    @PutMapping("/{id}")
    public ResponseEntity<BookingResponseDTO> changeBookingAppointmentTime(@PathVariable @Positive long id,
                                                                           @Valid @RequestBody BookingUpdateTimeDTO updateDTO) {
        //todo для ROLE_USER только своего расписания
        return ResponseEntity.ok(iService.updateBooking(id, updateDTO));
    }

    @Secured({"ROLE_SUPER_USER", "ROLE_OPERATOR"})
    @PatchMapping("/{id}")
    public ResponseEntity<BookingResponseDTO> updateBooking(@PathVariable @Positive long id,
                                                            @Valid @RequestBody BookingUpdateDiscountDTO updateDTO) {
        // todo implement method
        return ResponseEntity.ok(iService.updateBooking(id, updateDTO));
    }

    @Secured("ROLE_OPERATOR")
    @PutMapping("/{id}/discount")
    public ResponseEntity<BookingResponseDTO> changeDiscount(@PathVariable @Positive long id,
                                                             @Valid @RequestBody BookingUpdateDiscountDTO updateDTO) {
        return ResponseEntity.ok(iService.updateBooking(id, updateDTO));
    }

    @Secured({"ROLE_SUPER_USER", "ROLE_OPERATOR"})
    @PutMapping("/discount")
    public ResponseEntity<?> changeDiscountLimit(@Valid @RequestBody BookingUpdateDiscountDTO updateDTO) {
        //fixme нужно будет реализовать в будующих дз
        return null;
    }

    @Secured({"ROLE_USER", "ROLE_OPERATOR"})
    @GetMapping("/done")
    public ResponseEntity<List<BookingResponseDTO>> getProvidedService() {
        //todo все для оператора и только свои для user-а
        List<BookingResponseDTO> providedBookings = iService.getAllProvidedBookings();
        return ResponseEntity.ok(providedBookings);
    }

    @Secured({"ROLE_SUPER_USER", "ROLE_OPERATOR"})
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
