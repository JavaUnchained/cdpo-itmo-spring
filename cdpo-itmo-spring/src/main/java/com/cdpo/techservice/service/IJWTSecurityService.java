package com.cdpo.techservice.service;

import com.cdpo.techservice.dto.TokenDTO;
import com.cdpo.techservice.model.RoleType;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.proc.BadJOSEException;
import org.springframework.security.core.userdetails.UserDetails;

import java.text.ParseException;

public interface IJWTSecurityService {
    TokenDTO generateJWT(UserDetails userDetails, RoleType roleType);

    String getSubject(String token) throws BadJOSEException, ParseException, JOSEException;

    boolean isTokenValid(String token, UserDetails userDetails) throws BadJOSEException, ParseException, JOSEException;

    String getStringClaimsByKey(String token, String claimsKey) throws BadJOSEException, ParseException, JOSEException;
}
