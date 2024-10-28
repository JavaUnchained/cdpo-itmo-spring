package com.cdpo.techservice.service;

import com.cdpo.techservice.dto.TokenDTO;
import com.cdpo.techservice.model.RoleType;
import org.springframework.security.core.userdetails.UserDetails;

public interface IJWTSecurityService {
    TokenDTO generateJWT(UserDetails userDetails, RoleType roleType);
}
