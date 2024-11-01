package com.cdpo.techservice.controller;

import com.cdpo.techservice.dto.ServiceUserDto;
import com.cdpo.techservice.dto.ServiceUserUpdateDto;
import com.cdpo.techservice.service.IServiceAuthentication;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/profile")
public class ProfileController {
    private final IServiceAuthentication serviceAuthentication;
    @Secured({"ROLE_SUPER_USER", "ROLE_OPERATOR"})
    @PutMapping("/operator")
    public ResponseEntity<ServiceUserDto> updateOperatorProfile(@RequestBody @Valid ServiceUserUpdateDto serviceUserUpdateDto) {
        Authentication authenticationService = SecurityContextHolder.getContext().getAuthentication();
        String username = authenticationService.getName();
        return ResponseEntity.ok(serviceAuthentication.updateProfile(serviceUserUpdateDto, username));
    }


}
