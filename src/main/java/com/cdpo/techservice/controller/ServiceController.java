package com.cdpo.techservice.controller;

import com.cdpo.techservice.dto.ServiceRequestDTO;
import com.cdpo.techservice.dto.ServiceResponseDTO;
import com.cdpo.techservice.service.IServiceService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Validated
@RestController
@RequestMapping("/api/v1/services")
@AllArgsConstructor
public class ServiceController {
    private final IServiceService iService;

    @PostMapping
    public ResponseEntity<Long> createService(@RequestBody @Valid ServiceRequestDTO service) {
        return new ResponseEntity<>(iService.createService(service), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ServiceResponseDTO>> getServices(@Positive @RequestParam(required = false) Long id) {
        return ResponseEntity.ok(id == null ? iService.getAllServices() : iService.getServiceByIdAsList(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ServiceResponseDTO> updateService(@Positive @PathVariable long id,
                                                                   @RequestBody ServiceRequestDTO updates) {
        return ResponseEntity.ok(iService.updateService(id, updates));
    }


    @DeleteMapping
    public ResponseEntity<Void> deleteService(@Positive @RequestParam Long id) {
        iService.deleteService(id);
        return ResponseEntity.noContent().build();
    }

}