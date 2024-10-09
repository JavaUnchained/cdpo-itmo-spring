package com.cdpo.techservice.controller;

import com.cdpo.techservice.dto.BookingRequestDTO;
import com.cdpo.techservice.dto.BookingResponseDTO;
import com.cdpo.techservice.dto.BookingUpdateDTO;
import com.cdpo.techservice.service.IServiceBookingService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RestController
@RequestMapping("/api/v1/service/booking")
public class ServiceBookingController extends BaseController<IServiceBookingService> {

    @Autowired
    public ServiceBookingController(IServiceBookingService iService) {
        super(iService);
    }

    @PostMapping
    public ResponseEntity<Long> createBooking(@RequestBody @Valid  BookingRequestDTO requestBooking) {
        long createdId = iService.createBooking(requestBooking);
        return createdId == -1 ? ResponseEntity.badRequest().build() : new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @DeleteMapping
    public ResponseEntity<Void> cancelBooking(@RequestParam @Positive Long id) {
        return iService.deleteBooking(id) ? ResponseEntity.noContent().build() : buildNotFound();
    }

    @GetMapping
    public ResponseEntity<List<BookingResponseDTO>> getBookings(@RequestParam(required = false) Long id) {
        return id == null ?
                ResponseEntity.ok(iService.getAllBookings()) :
                iService.getBookingByIdAsList(id)
                        .map(ResponseEntity::ok)
                        .orElseGet(ServiceController::buildNotFound);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookingResponseDTO> changeBookingAppointmentTime(@PathVariable @Positive long id,
                                                                           @Valid @RequestBody BookingUpdateDTO updateDTO) {
        return iService.updateBooking(id, updateDTO)
                .map(ResponseEntity::ok)
                .orElseGet(ServiceController::buildNotFound);
    }

}
