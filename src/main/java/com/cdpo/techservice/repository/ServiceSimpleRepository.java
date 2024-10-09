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
    public long createService(String name, String description){
        long id = idGenerator.incrementAndGet();
        Service service = new Service(id, name, description);
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
    public Service updateServiceById(long id, String name, String description) {
        Service serviceById = getServiceById(id);
        if (serviceById != null) {
            if (name != null) {
                serviceById.setName(name);
            }
            if (description != null) {
                serviceById.setDescription(description);
            }
        }
        return serviceById;
    }

    @Override
    public boolean deleteServiceById(long id) {
        return services.removeIf(s -> s.getId() == id);
    }
}
