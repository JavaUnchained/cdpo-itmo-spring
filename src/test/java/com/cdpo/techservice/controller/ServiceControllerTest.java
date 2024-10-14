package com.cdpo.techservice.controller;

import com.cdpo.techservice.dto.ServiceRequestDTO;
import com.cdpo.techservice.dto.ServiceResponseDTO;
import com.cdpo.techservice.repository.ServiceSimpleRepository;
import com.cdpo.techservice.service.IServiceService;
import com.cdpo.techservice.service.ServiceService;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ServiceControllerTest {


    // Creating a service with valid data returns a 201 status and service ID
    @Test
    public void test_create_service_with_valid_data() {
        ServiceSimpleRepository serviceSimpleRepository = new ServiceSimpleRepository();
        ServiceService serviceRepository = new ServiceService(serviceSimpleRepository);
        ServiceController serviceController = new ServiceController(serviceRepository);
        ServiceRequestDTO serviceRequest = new ServiceRequestDTO("Test Service", "Test Description", (long) (60*60*10), 120.0);

        ResponseEntity<Long> response = serviceController.createService(serviceRequest);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    // Retrieving all services returns a list of services
    @Test
    public void test_retrieve_all_services() {
        IServiceService mockServiceService = mock(IServiceService.class);

        ServiceController serviceController = new ServiceController(mockServiceService);

        List<ServiceResponseDTO> mockServiceList = new ArrayList<>();
        mockServiceList.add(new ServiceResponseDTO(1L, new ServiceRequestDTO("Service 1", "Description 1", 10L, 100.0)));
        mockServiceList.add(new ServiceResponseDTO(2L, new ServiceRequestDTO("Service 2", "Description 2", (long) 101L, 200.0)));

        when(mockServiceService.getAllServices()).thenReturn(mockServiceList);

        ResponseEntity<List<ServiceResponseDTO>> response = serviceController.getServices(null);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, Objects.requireNonNull(response.getBody()).size());
    }

    // Updating a service with valid ID and data returns updated service details
    @Test
    public void test_update_service_with_valid_data() {
        IServiceService mockServiceRepository = mock(IServiceService.class);
        ServiceController serviceController = new ServiceController(mockServiceRepository);

        long id = 1L;
        ServiceRequestDTO updates = new ServiceRequestDTO("Updated Service", "Updated Description", 1000L, 200.0);

        when(mockServiceRepository.updateService(id, updates)).thenReturn(Optional.of(new ServiceResponseDTO(id, updates)));

        ResponseEntity<ServiceResponseDTO> response = serviceController.updateService(id, updates);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(updates.name(), response.getBody().name());
        assertEquals(updates.description(), response.getBody().description());
    }

    // Deleting a service with valid ID returns a 204 status
    @Test
    public void test_delete_service_with_valid_id_returns_204() {
        IServiceService mockServiceRepository = mock(IServiceService.class);
        ServiceController serviceController = new ServiceController(mockServiceRepository);
        long validId = 1L;

        when(mockServiceRepository.deleteService(validId)).thenReturn(true);

        ResponseEntity<Void> response = serviceController.deleteService(validId);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    // Deleting a service with a non-existent ID returns a 404 status
    @Test
    public void test_delete_non_existent_service_returns_404() {
        IServiceService mockServiceRepository = mock(IServiceService.class);
        ServiceController serviceController = new ServiceController(mockServiceRepository);

        long nonExistentId = 9999L;
        when(mockServiceRepository.deleteService(nonExistentId)).thenReturn(false);

        ResponseEntity<Void> response = serviceController.deleteService(nonExistentId);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    // Updating a service with a non-existent ID returns a 404 status
    @Test
    public void test_update_non_existent_service_returns_404() {
        IServiceService serviceRepository = mock(IServiceService.class);
        ServiceController serviceController = new ServiceController(serviceRepository);

        long nonExistentId = 9999L;
        ServiceRequestDTO updates = new ServiceRequestDTO("Updated Name", "Updated Description", 100000L, 123.0);
        when(serviceRepository.updateService(nonExistentId, updates)).thenReturn(Optional.empty());
        ResponseEntity<ServiceResponseDTO> response = serviceController.updateService(nonExistentId, updates);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    // Retrieving a service with a non-existent ID returns a 404 status
    @Test
    public void test_retrieve_non_existent_service_returns_404() {
        IServiceService mockServiceRepository = mock(IServiceService.class);
        ServiceController serviceController = new ServiceController(mockServiceRepository);

        long nonExistentId = 1000L;
        when(mockServiceRepository.getServiceById(nonExistentId)).thenReturn(Optional.empty());

        ResponseEntity<List<ServiceResponseDTO>> response = serviceController.getServices(nonExistentId);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }
}