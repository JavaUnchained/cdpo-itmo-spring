package com.cdpo.techservice.service;

import com.cdpo.techservice.dto.ServiceRequestDTO;
import com.cdpo.techservice.dto.ServiceResponseDTO;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Validated
public interface IServiceService {

    /**
     * Method to create a new service
     *
     * @param service new service
     * @return id of saved service
     */
    long createService(ServiceRequestDTO service);

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
    ServiceResponseDTO getServiceById(long id);

    default List<ServiceResponseDTO> getServiceByIdAsList(long id) {
        return List.of(getServiceById(id));
    }

    /**
     * Method to update an existing service
     *
     * @param id             id existed service
     * @param updates new data to update
     * @return updated service
     */
    ServiceResponseDTO updateService(long id, ServiceRequestDTO updates);

    /**
     * Method to delete a service by ID
     *
     * @param id target id to delete
     */
    void deleteService(long id);
}
