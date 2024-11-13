package com.cdpo.techservice.mapper;

import com.cdpo.techservice.dto.ServiceRequestDTO;
import com.cdpo.techservice.dto.ServiceResponseDTO;
import com.cdpo.techservice.model.Service;

@org.springframework.stereotype.Service
public class ServiceMapper {

    public ServiceResponseDTO toDTO(Service service) {
        return getServiceResponseDTO(service, service.getPrice());
    }

    public Service toEntity(ServiceRequestDTO service) {
        Service entity =  new Service();
        entity.setName(service.name());
        entity.setDescription(service.description());
        entity.setDuration(service.duration());
        entity.setPrice(service.price());
        return entity;
    }

    public ServiceResponseDTO toDTOWithDiscount(Service service, Double discountPercent) {
        return getServiceResponseDTO(service,
                discountPercent == null ? service.getPrice() : (service.getPrice() * (discountPercent / 100)));

    }

    private static ServiceResponseDTO getServiceResponseDTO(Service service, double price) {
        return new ServiceResponseDTO(
                service.getId(),
                service.getName(),
                service.getDescription(),
                service.getDuration(),
                price);
    }
}
