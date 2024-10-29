package com.cdpo.techservice.service;

import com.cdpo.techservice.dto.TokenDTO;
import com.cdpo.techservice.exception.AuthenticationException;
import com.cdpo.techservice.model.RoleType;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.DirectEncrypter;
import com.nimbusds.jose.proc.BadJOSEException;
import com.nimbusds.jose.proc.SimpleSecurityContext;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.proc.ConfigurableJWTProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

@Service
public class JWTSecurityService implements IJWTSecurityService {
    private static final String ALLOWS_OPERATION_CLAIM = "allowsOperation";
    private static final String CITY_CLAIM = "city";

    @Value("${security.jwtSecretExpiration}")
    private long jwtSecretExpiration;

    private final JWEEncrypter jweEncrypter;
    private final ConfigurableJWTProcessor<SimpleSecurityContext> jwtProcessor;

    @Autowired
    public JWTSecurityService(@Qualifier("jwtEncrypter") JWEEncrypter jweEncrypter,
                              @Qualifier("jwtProcessor") ConfigurableJWTProcessor<SimpleSecurityContext> jwtProcessor) {
        this.jweEncrypter = jweEncrypter;
        this.jwtProcessor = jwtProcessor;
    }

    @Override
    public TokenDTO generateJWT(UserDetails userDetails, RoleType roleType) {
        try {
            return new TokenDTO(generateToken(userDetails, roleType), generateRefreshToken());
        } catch (JOSEException e) {
            throw new AuthenticationException(HttpStatus.FORBIDDEN, e.getMessage());
        }
    }

    @Override
    public String getSubject(String token) throws BadJOSEException, ParseException, JOSEException {
        return extractClaims(token).getSubject();
    }

    @Override
    public boolean isTokenValid(String token, UserDetails userDetails) throws BadJOSEException, ParseException, JOSEException {
        return getSubject(token).equals(userDetails.getUsername())
                && extractClaims(token).getExpirationTime().after(new Date());
    }

    @Override
    public String getStringClaimsByKey(String token, String claimsKey) throws BadJOSEException, ParseException, JOSEException {
        return extractClaims(token).getStringClaim(claimsKey);
    }

    private JWTClaimsSet extractClaims(String token) throws BadJOSEException, ParseException, JOSEException {
        return jwtProcessor.process(token, null);
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
                .claim(ALLOWS_OPERATION_CLAIM, roleType.getPrivileges().toString()) //just for example
                .issueTime(new Date(timeMillis))
                .expirationTime(new Date(timeMillis + jwtSecretExpiration))
                .build()
                .toJSONObject();
    }

    private void encrypt(JWEObject jweObject) throws JOSEException {
        jweObject.encrypt(jweEncrypter);
    }
}
