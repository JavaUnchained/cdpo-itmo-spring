package com.cdpo.techservice.preinstall;

import com.cdpo.techservice.dto.ServiceUserDto;
import com.cdpo.techservice.exception.AuthenticationException;
import com.cdpo.techservice.model.RoleType;
import com.cdpo.techservice.service.IServiceAuthentication;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
@RequiredArgsConstructor
@Component
public class DefaultUsersCreator implements CommandLineRunner {
    private final IServiceAuthentication serviceAuthentication;

    @Override
    public void run(String... args) throws Exception {
        ServiceUserDto serviceUserDto = new ServiceUserDto("admin", "admin", "admin", "admin@mail.ru", "123123123");
        try {
            serviceAuthentication.registration(serviceUserDto, RoleType.ROLE_SUPER_USER);
        } catch (AuthenticationException e) {
            //ignore
        }
        serviceUserDto = new ServiceUserDto("operator", "operaator", "operator", "operator@mail.ru", "123123123");
        try {
            serviceAuthentication.registration(serviceUserDto, RoleType.ROLE_SUPER_USER);
        } catch (AuthenticationException e) {
            //ignore
        }
        serviceUserDto = new ServiceUserDto("user", "user", "user", "user@mail.ru", "123123123");
        try {
            serviceAuthentication.registration(serviceUserDto);
        } catch (AuthenticationException e) {
            //ignore
        }
    }
}
