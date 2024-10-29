package com.cdpo.techservice.controller;

import com.cdpo.techservice.dto.ServiceUserDto;
import com.cdpo.techservice.dto.TokenDTO;
import com.cdpo.techservice.service.IServiceAuthentication;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/auth")
public class AuthenticationController {
    private final IServiceAuthentication serviceAuthentication;

    @ResponseBody
    @PostMapping("/login")
    public ResponseEntity<TokenDTO> loginAccount(@RequestParam("application_user_username") String username,
                                                 @RequestParam("application_user_password") String password) {
        return ResponseEntity.ok(serviceAuthentication.loginAccount(username, password));
    }

    @PostMapping("/registration")
    public ResponseEntity<Void> createAccount(ServiceUserDto user, Model model) {
        serviceAuthentication.registration(user);
        return ResponseEntity.noContent().build();
    }

}
