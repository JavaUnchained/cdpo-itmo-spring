package com.cdpo.techservice.service;

import com.cdpo.techservice.dto.ServiceRequestDTO;
import com.cdpo.techservice.dto.ServiceResponseDTO;
import com.cdpo.techservice.mapper.ServiceMapper;
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
    private final ServiceMapper serviceMapper;

    @Override
    public long createService(ServiceRequestDTO service) {
        if (service.description() != null && service.description().isBlank()) {
            return -1;
        }
        return serviceRepository.save(serviceMapper.toEntity(service)).getId();
    }

    @Override
    public List<ServiceResponseDTO> getAllServices() {
        return serviceRepository.findAll()
                .stream().map(serviceMapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public Optional<ServiceResponseDTO> getServiceById(long id) {
        return serviceRepository.findById(id).map(serviceMapper::toDTO);
    }

    @Override
    public Optional<ServiceResponseDTO> updateService(long id, ServiceRequestDTO updates) {
        Service service = serviceRepository.findById(id).get();
        if (updates.name() != null) {
            service.setName(updates.name());
        }
        if (updates.description() != null) {
            service.setDescription(updates.description());
        }
        if (updates.duration() != null) {
            service.setDuration(updates.duration());
        }
        if(updates.price() != null) {
            service.setPrice(updates.price());
        }

        serviceRepository.update(
                service.getName(),
                service.getDescription(),
                service.getDuration(),
                service.getPrice(),
                service.getId()
        );


        return Optional.ofNullable(serviceMapper.toDTO(service));
    }

    @Override
    public boolean deleteService(long id) {
        serviceRepository.deleteById(id);
        return true;
    }
}