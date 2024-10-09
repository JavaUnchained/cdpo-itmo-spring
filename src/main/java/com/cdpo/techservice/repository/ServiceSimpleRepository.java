package com.cdpo.techservice.repository;

import com.cdpo.techservice.model.Service;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Primary
@Repository
public class ServiceSimpleRepository implements IServiceRepository{
    private final List<Service> services = new ArrayList<>();
    private final AtomicLong idGenerator = new AtomicLong();

    @Override
    public long createService(Service service) {
        long id = idGenerator.incrementAndGet();
        service.setId(id);
        services.add(service);
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
    public Service updateServiceById(long id, Service updates) {
        Service serviceById = getServiceById(id);
        if (serviceById != null) {
            if (updates.getName() != null) {
                serviceById.setName(updates.getName());
            }
            if (updates.getDescription() != null) {
                serviceById.setDescription(updates.getDescription());
            }
        }
        return serviceById;
    }

    @Override
    public boolean deleteServiceById(long id) {
        return services.removeIf(s -> s.getId() == id);
    }
}
