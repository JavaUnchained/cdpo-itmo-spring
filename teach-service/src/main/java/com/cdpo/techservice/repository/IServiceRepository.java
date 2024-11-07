package com.cdpo.techservice.repository;

import com.cdpo.techservice.model.Service;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface IServiceRepository extends JpaRepository<Service,Long>{


    @Transactional
    @Modifying
    @Query("update Service s set s.name = ?1, s.description = ?2, s.duration = ?3, s.price = ?4 where s.id = ?5")
    void update(String name, String description, Long duration, Double price, long id);
}

