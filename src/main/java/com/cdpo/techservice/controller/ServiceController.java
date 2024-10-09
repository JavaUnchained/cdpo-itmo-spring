package com.cdpo.techservice.controller;

import com.cdpo.techservice.dto.ServiceRequestDTO;
import com.cdpo.techservice.dto.ServiceResponseDTO;
import com.cdpo.techservice.service.IServiceService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Validated
@RestController
@RequestMapping("/api/v1/services")
@RequiredArgsConstructor
public class ServiceController {

    private final IServiceService serviceRepository;

    /**
     * Create a new service (POST)
     *
     * @param service service to create
     * @return id of created service
     */
    @PostMapping
    public ResponseEntity<Long> createService(@RequestBody @Valid ServiceRequestDTO service) {
        if (service.description() != null && service.description().isBlank()) {
            return ResponseEntity.badRequest().build();
        }
        return new ResponseEntity<>(serviceRepository.createService(service), HttpStatus.CREATED);
    }

    /**
     * Retrieve all services or a specific service by ID (GET)
     *
     * @param id target service
     * @return one or all services
     */
    @GetMapping
    public ResponseEntity<List<ServiceResponseDTO>> getServices(@Positive @RequestParam(required = false) Long id) {
        if (id != null) {
            return serviceRepository.getServiceById(id)
                    .map(List::of)
                    .map(ResponseEntity::ok)
                    .orElseGet(() -> ResponseEntity.notFound().build());
        }
        return ResponseEntity.ok(serviceRepository.getAllServices());
    }

    /**
     * Update an existing service by ID (PUT)
     *
     * @param id id to update
     * @param updates new data for service
     * @return changed service
     */
    @PutMapping("/{id}")
    public ResponseEntity<ServiceResponseDTO> updateService(@Positive @PathVariable long id,
                                                                   @RequestBody ServiceRequestDTO updates) {
        return serviceRepository.updateService(id, updates)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }


    /**
     * Delete service by id
     *
     * @param id target service id
     */
    @DeleteMapping
    public ResponseEntity<Void> deleteService(@Positive @RequestParam Long id) {
        return serviceRepository.deleteService(id) ?
                ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}