package com.cdpo.techservice.service;

import com.cdpo.techservice.dto.ServiceRequestDTO;
import com.cdpo.techservice.dto.ServiceResponseDTO;
import com.cdpo.techservice.model.Service;
import com.cdpo.techservice.repository.IServiceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Primary
@org.springframework.stereotype.Service
@RequiredArgsConstructor
public class ServiceService implements IServiceService {
    private final IServiceRepository serviceRepository;

    @Override
    public long createService(ServiceRequestDTO service) {
        if (service.description() != null && service.description().isBlank()) {
            return -1;
        }
        return serviceRepository.createService(
                service.name(),
                service.description(),
                service.duration(),
                service.price());
    }

    @Override
    public List<ServiceResponseDTO> getAllServices() {
        return serviceRepository.getAllServices().stream()
                .map(ServiceService::serviceToResponseDTO).collect(Collectors.toList());
    }

    @Override
    public Optional<ServiceResponseDTO> getServiceById(long id) {
        return Optional.ofNullable(serviceRepository.getServiceById(id))
                .map(ServiceService::serviceToResponseDTO);
    }

    @Override
    public Optional<ServiceResponseDTO> updateService(long id, ServiceRequestDTO updates) {
        return Optional.ofNullable(
                serviceRepository.updateServiceById(id, updates.name(), updates.description())
        ).map(ServiceService::serviceToResponseDTO);
    }

    @Override
    public boolean deleteService(long id) {
        return serviceRepository.deleteServiceById(id);
    }

    private static ServiceResponseDTO serviceToResponseDTO(Service service) {
        return new ServiceResponseDTO(
                service.getId(),
                service.getName(),
                service.getDescription(),
                service.getDuration(),
                service.getPrice());
    }
}