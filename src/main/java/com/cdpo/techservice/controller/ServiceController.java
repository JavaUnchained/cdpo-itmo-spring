package com.cdpo.techservice.controller;

import com.cdpo.techservice.dto.ServiceRequestDTO;
import com.cdpo.techservice.dto.ServiceResponseDTO;
import com.cdpo.techservice.repository.IServiceRepository;
import com.cdpo.techservice.repository.ServiceRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@Validated
@RestController
@RequestMapping("/api/v1/services")
public class ServiceController {
    private final IServiceRepository serviceRepository;

    @Autowired
    public ServiceController(ServiceRepository serviceRepository) {
        this.serviceRepository = serviceRepository;
    }

    /**
     * Create a new service (POST)
     *
     * @param service service to create
     * @return id of created service
     */
    @PostMapping
    public ResponseEntity<Long> createService(@Valid @RequestBody ServiceRequestDTO service) {
        if (service.getName() == null || service.getDescription() == null) {
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
    public ResponseEntity<List<@Valid ServiceResponseDTO>> getServices(@Positive @RequestParam(required = false) Long id) {
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
     * @param updatedService new data for service
     * @return changed service
     */
    @PutMapping("/{id}")
    public ResponseEntity<@Valid ServiceResponseDTO> updateService(@Positive @PathVariable long id,
                                                                   @NotNull @NotEmpty Map<String, Object> updates) {
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