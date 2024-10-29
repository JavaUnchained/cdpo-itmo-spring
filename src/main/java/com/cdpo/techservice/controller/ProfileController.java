package com.cdpo.techservice.controller;

import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/profile")
public class ProfileController {

    @Secured({"ROLE_SUPER_USER", "ROLE_OPERATOR"})
    @PutMapping("/operator")
    public void updateOperatorProfile() {
        //todo implement
        //todo если редактирует оператор то только свой профиль админ любой
    }


}
