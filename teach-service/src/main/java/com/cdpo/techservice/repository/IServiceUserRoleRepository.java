package com.cdpo.techservice.repository;

import com.cdpo.techservice.model.RoleType;
import com.cdpo.techservice.model.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IServiceUserRoleRepository extends JpaRepository<UserRole, Long>{
    Optional<UserRole> findByRoleType(RoleType roleType);
}
