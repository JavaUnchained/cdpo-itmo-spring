package com.cdpo.techservice.repository;

import com.cdpo.techservice.model.ServiceUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IServiceUserRepository extends JpaRepository<ServiceUser, Long> {
    Optional<ServiceUser> findByUsername(String username);
    boolean existsByUsername(String username);
}
