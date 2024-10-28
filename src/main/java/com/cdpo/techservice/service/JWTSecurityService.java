package com.cdpo.techservice.service;

import com.cdpo.techservice.dto.TokenDTO;
import com.cdpo.techservice.exception.AuthenticationException;
import com.cdpo.techservice.model.RoleType;
import com.nimbusds.jose.*;
import com.nimbusds.jwt.JWTClaimsSet;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;
import java.util.UUID;

@Service
public class JWTSecurityService implements IJWTSecurityService {
    public static final String CITY_CLAIM = "city";
    public static final String ALLOWS_OPERATION_CLAIM = "allowsOperation";
    @Value("${security.jwtSecret}")
    private String jwtSecret;
    @Value("${security.jwtRefreshSecret}")
    private String jwtRefreshSecret;
    @Value("${security.jwtSecretExpiration}")
    private long jwtSecretExpiration;

    @Override
    public TokenDTO generateJWT(UserDetails userDetails, RoleType roleType) {
        try {
            return new TokenDTO(generateToken(userDetails, roleType), generateRefreshToken());
        } catch (JOSEException e) {
            throw new AuthenticationException(e.getMessage());
        }
    }

    private String generateRefreshToken() {
        return UUID.randomUUID().toString();
    }

    private String generateToken(UserDetails userDetails, RoleType roleType) throws JOSEException {
        JWEObject jweObject = new JWEObject(getHeader(), getPayload(userDetails.getUsername(), roleType));
        encrypt(jweObject);
        return jweObject.serialize();
    }

    private JWEHeader getHeader() {
        return new JWEHeader(JWEAlgorithm.DIR, EncryptionMethod.A128CBC_HS256);
    }

    private Payload getPayload(String subject, RoleType roleType) {
        return new Payload(buildPayload(subject, roleType));
    }

    private Map<String, Object> buildPayload(String subject, RoleType roleType) {
        long timeMillis = System.currentTimeMillis();
        return new JWTClaimsSet.Builder()
                .subject(subject)
                .claim(CITY_CLAIM, "saint-petersburg")
                .claim(ALLOWS_OPERATION_CLAIM, roleType.getPrivileges().toString())
                .issueTime(new Date(timeMillis))
                .expirationTime(new Date(timeMillis + jwtSecretExpiration))
                .build()
                .toJSONObject();
    }

    private void encrypt(JWEObject jweObject) throws JOSEException {
        jweObject.encrypt((JWEEncrypter) webApplicationContext.getBean("JWEEncrypter"));
    }
}
