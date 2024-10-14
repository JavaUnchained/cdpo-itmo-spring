package com.cdpo.techservice.repository;

import com.cdpo.techservice.model.Service;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Primary
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
@Repository
public class ServiceSimpleRepository implements IServiceRepository{
    private final List<Service> services = new ArrayList<>();
    private final AtomicLong idGenerator = new AtomicLong();

    @Override
    public long createService(String name, String description, long duration, double price){
        long id = idGenerator.incrementAndGet();
        services.add(new Service(id, name, description, duration, price));
        return id;
    }

    @Override
    public List<Service> getAllServices() {
        return new ArrayList<>(services);
    }

    @Override
    public Service getServiceById(long id) {
        return services.stream().filter(s -> s.getId() == id).findFirst().orElse(null);
    }

    @Override
    public List<Service> getServicesByIds(List<Long> ids) {
        return services.stream().filter(s -> ids.contains(s.getId())).collect(Collectors.toList());
    }

    @Override
    public Service updateServiceById(long id, String name, String description, Long duration, Double price) {
        Service serviceById = getServiceById(id);
        if (serviceById != null) {
            if (name != null) {
                serviceById.setName(name);
            }
            if (description != null) {
                serviceById.setDescription(description);
            }
            if (duration != null) {
                serviceById.setDuration(duration);
            }
            if (price != null) {
                serviceById.setPrice(price);
            }
        }
        return serviceById;
    }

    @Override
    public boolean deleteServiceById(long id) {
        return services.removeIf(s -> s.getId() == id);
    }
}
