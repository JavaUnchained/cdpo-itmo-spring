package com.cdpo.techservice.repository;

import com.cdpo.techservice.model.Service;

import java.util.List;

public interface IServiceRepository {
    /**
     * Method to create a new service
     */
    long createService(String name, String description);

    /**
     * Method to get all services
     *
     * @return services
     */
    List<Service> getAllServices();

    /**
     * Method to get a service by ID
     *
     * @param id id of target service
     * @return target service
     */
    Service getServiceById(long id);

    /**
     * Method to update an existing service
     *
     * @return updated service
     */
    Service updateServiceById(long id, String name, String description);

    /**
     * Method to delete a service by ID
     *
     * @param id target id to delete
     * @return delete status
     */
    boolean deleteServiceById(long id);
}
