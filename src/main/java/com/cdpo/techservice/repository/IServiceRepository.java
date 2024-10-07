package com.cdpo.techservice.repository;

import com.cdpo.techservice.dto.ServiceRequestDTO;
import com.cdpo.techservice.dto.ServiceResponseDTO;

import java.util.List;
import java.util.Optional;

public interface IServiceRepository {

    /**
     * Method to create a new service
     *
     * @param service new service
     * @return id of saved servicee
     */
    Long createService(ServiceRequestDTO service);

    /**
     * Method to get all services
     *
     * @return services
     */
    List<ServiceResponseDTO> getAllServices();

    /**
     * Method to get a service by ID
     *
     * @param id id of target service
     * @return target service
     */
    Optional<ServiceResponseDTO> getServiceById(long id);

    /**
     * Method to update an existing service
     *
     * @param id             id existed service
     * @param updatedService new data to update
     * @return updated service
     */
    Optional<ServiceResponseDTO> updateService(long id, ServiceRequestDTO updatedService);

    /**
     * Method to delete a service by ID
     *
     * @param id target id to delete
     * @return delete status
     */
    boolean deleteService(long id);
}
