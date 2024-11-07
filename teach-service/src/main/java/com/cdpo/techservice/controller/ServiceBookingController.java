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
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
        Authentication authenticationService = SecurityContextHolder.getContext().getAuthentication();
        String username = authenticationService.getName();
        return new ResponseEntity<>(iService.createBooking(requestBooking, username), HttpStatus.CREATED);
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
        Authentication authenticationService = SecurityContextHolder.getContext().getAuthentication();
        String username = authenticationService.getName();
        return ResponseEntity.ok(stateDTO != null ? iService.getAllFilteredBy(stateDTO, username) : iService.getAllBookings(username));
    }

    @Secured({"ROLE_USER", "ROLE_OPERATOR"})
    @GetMapping("/{id}")
    public ResponseEntity<BookingResponseDTO> getBooking(@PathVariable long id) {
        Authentication authenticationService = SecurityContextHolder.getContext().getAuthentication();
        String username = authenticationService.getName();
        return ResponseEntity.ok(iService.getBookingById(id, username));
    }

    @Secured({"ROLE_USER", "ROLE_OPERATOR"})
    @PutMapping("/{id}/appointment")
    public ResponseEntity<BookingResponseDTO> changeBookingAppointmentTime(@PathVariable @Positive long id,
                                                                           @Valid @RequestBody BookingUpdateTimeDTO updateDTO) {
        Authentication authenticationService = SecurityContextHolder.getContext().getAuthentication();
        String username = authenticationService.getName();
        return ResponseEntity.ok(iService.updateBooking(id, updateDTO, username));
    }

    @Secured({"ROLE_SUPER_USER", "ROLE_OPERATOR"})
    @PutMapping("/{id}")
    public ResponseEntity<BookingResponseDTO> updateBooking(@PathVariable @Positive long id,
                                                            @Valid @RequestBody BookingDTO updateDTO) {
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
    public ResponseEntity<String> changeDiscountLimit(@Valid @RequestBody BookingUpdateDiscountDTO updateDTO) {
        // этот метод пока не нужен в дз
        return ResponseEntity.ok("Not implemented");
    }

    @Secured({"ROLE_USER", "ROLE_OPERATOR"})
    @GetMapping("/done")
    public ResponseEntity<List<BookingResponseDTO>> getProvidedService() {
        Authentication authenticationService = SecurityContextHolder.getContext().getAuthentication();
        String username = authenticationService.getName();
        List<BookingResponseDTO> providedBookings = iService.getAllProvidedBookings(username);
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
