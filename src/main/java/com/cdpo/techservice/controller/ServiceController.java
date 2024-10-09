package com.cdpo.techservice.controller;

import com.cdpo.techservice.dto.ServiceRequestDTO;
import com.cdpo.techservice.dto.ServiceResponseDTO;
import com.cdpo.techservice.service.IServiceService;
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
@RequestMapping("/api/v1/services")
public class ServiceController extends BaseController<IServiceService>{

    @Autowired
    public ServiceController(IServiceService serviceService) {
        super(serviceService);
    }

    @PostMapping
    public ResponseEntity<Long> createService(@RequestBody @Valid ServiceRequestDTO service) {
        long createdId = iService.createService(service);
        return createdId == -1 ? ResponseEntity.badRequest().build() : new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ServiceResponseDTO>> getServices(@Positive @RequestParam(required = false) Long id) {
        return id == null ?
                ResponseEntity.ok(iService.getAllServices()) :
                iService.getServiceByIdAsList(id)
                        .map(ResponseEntity::ok)
                        .orElseGet(ServiceController::buildNotFound);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ServiceResponseDTO> updateService(@Positive @PathVariable long id,
                                                                   @RequestBody ServiceRequestDTO updates) {
        return iService.updateService(id, updates)
                .map(ResponseEntity::ok)
                .orElseGet(ServiceController::buildNotFound);
    }


    @DeleteMapping
    public ResponseEntity<Void> deleteService(@Positive @RequestParam Long id) {
        return iService.deleteService(id) ?
                ResponseEntity.noContent().build() : buildNotFound();
    }
}