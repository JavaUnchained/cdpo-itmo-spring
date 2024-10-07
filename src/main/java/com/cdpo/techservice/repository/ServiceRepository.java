package com.cdpo.techservice.repository;

import com.cdpo.techservice.dto.ServiceRequestDTO;
import com.cdpo.techservice.dto.ServiceResponseDTO;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Primary
@Repository
public class ServiceRepository implements IServiceRepository{

    public static final String NAME = "name";
    public static final String DESCRIPTION = "description";
    private final List<ServiceResponseDTO> services = new ArrayList<>();
    private final AtomicLong idGenerator = new AtomicLong();

    @Override
    public long createService(ServiceRequestDTO service) {
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
    public Optional<ServiceResponseDTO> updateService(long id, Map<String, Object> updates) {
        Optional<ServiceResponseDTO> existingServiceOpt = getServiceById(id);
        if (existingServiceOpt.isPresent()) {
            ServiceResponseDTO existingService = existingServiceOpt.get();
            if (updates.containsKey(NAME)) {
                existingService.setName((String) updates.get(NAME));
            }
            if (updates.containsKey(DESCRIPTION)) {
                existingService.setDescription((String) updates.get(DESCRIPTION));
            }
            return Optional.of(existingService);
        }
        return Optional.empty();
    }

    @Override
    public boolean deleteService(long id) {
        return services.removeIf(s -> s.getId() == id);
    }
}