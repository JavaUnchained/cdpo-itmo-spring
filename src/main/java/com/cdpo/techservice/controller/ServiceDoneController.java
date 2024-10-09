package com.cdpo.techservice.controller;


import com.cdpo.techservice.dto.BookingResponseDTO;
import com.cdpo.techservice.service.IServiceBookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Validated
@RestController
@RequestMapping("/api/v1/services/done")
public class ServiceDoneController extends BaseController<IServiceBookingService>{

    @Autowired
    public ServiceDoneController(IServiceBookingService iService) {
        super(iService);
    }

    @GetMapping
    public ResponseEntity<List<BookingResponseDTO>> getProvidedService() {
        List<BookingResponseDTO> providedBookings = iService.getAllProvidedBookings();
        return providedBookings.isEmpty() ? buildNotFound() : ResponseEntity.ok(providedBookings);
    }
}
