package com.cdpo.techservice.repository;

import com.cdpo.techservice.dto.ServiceRequestDTO;
import com.cdpo.techservice.dto.ServiceResponseDTO;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class ServiceRepository implements IServiceRepository{

    private final List<ServiceResponseDTO> services = new ArrayList<>();
    private final AtomicLong idGenerator = new AtomicLong();

    @Override
    public Long createService(ServiceRequestDTO service) {
        long id = idGenerator.incrementAndGet();
        services.add(new ServiceResponseDTO(id, service));
        return id;
    }

    @Override
    public List<ServiceResponseDTO> getAllServices() {
        return new ArrayList<>(services);
    }

    @Override
    public Optional<ServiceResponseDTO> getServiceById(long id) {
        return services.stream()
                .filter(s -> s.getId() == id)
                .findFirst();
    }

    @Override
    public Optional<ServiceResponseDTO> updateService(long id, ServiceRequestDTO updatedService) {
        Optional<ServiceResponseDTO> existingServiceOpt = getServiceById(id);
        if (existingServiceOpt.isPresent()) {
            ServiceResponseDTO existingService = existingServiceOpt.get();
            existingService.setName(updatedService.getName());
            existingService.setDescription(updatedService.getDescription());
            return Optional.of(existingService);
        }
        return Optional.empty();
    }

    @Override
    public boolean deleteService(long id) {
        return services.removeIf(s -> s.getId() == id);
    }
}