package com.cdpo.techservice.service;

import com.cdpo.techservice.dto.ServiceRequestDTO;
import com.cdpo.techservice.dto.ServiceResponseDTO;
import com.cdpo.techservice.exception.IllegalInputParameters;
import com.cdpo.techservice.exception.NotFoundException;
import com.cdpo.techservice.mapper.ServiceMapper;
import com.cdpo.techservice.model.Service;
import com.cdpo.techservice.repository.IServiceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;

import java.util.List;
import java.util.stream.Collectors;

@Primary
@org.springframework.stereotype.Service
@RequiredArgsConstructor
public class ServiceService implements IServiceService {
    private static final String DESCRIPTION_CANNOT_BE_BLANK = "Description cannot be blank";
    private static final String NOT_FOUND = "Service not found";

    private final IServiceRepository serviceRepository;
    private final ServiceMapper serviceMapper;

    @Override
    public long createService(ServiceRequestDTO service) {
        if (service.description() != null && service.description().isBlank()) {
            throw new IllegalInputParameters(DESCRIPTION_CANNOT_BE_BLANK);
        }
        return serviceRepository.save(serviceMapper.toEntity(service)).getId();
    }

    @Override
    public List<ServiceResponseDTO> getAllServices() {
        List<Service> services = serviceRepository.findAll();
        if(services.isEmpty()) throw new NotFoundException(NOT_FOUND);
        return services.stream().map(serviceMapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public ServiceResponseDTO getServiceById(long id) {
        return serviceRepository.findById(id)
                .map(serviceMapper::toDTO).orElseThrow(() -> new NotFoundException(NOT_FOUND));
    }

    @Override
    public ServiceResponseDTO updateService(long id, ServiceRequestDTO updates) {
        Service service = serviceRepository.findById(id).orElseThrow(() -> new NotFoundException(NOT_FOUND));
        mergeToUpdate(updates, service);

        serviceRepository.update(
                service.getName(), service.getDescription(), service.getDuration(), service.getPrice(), service.getId());

        return serviceMapper.toDTO(service);
    }

    private static void mergeToUpdate(ServiceRequestDTO updates, Service service) {
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
    }

    @Override
    public void deleteService(long id) {
        serviceRepository.deleteById(id);
    }
}