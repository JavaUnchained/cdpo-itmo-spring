package com.cdpo.techservice.service;

import com.cdpo.techservice.dto.ServiceUserDto;
import com.cdpo.techservice.dto.TokenDTO;

public interface IServiceAuthentication {
    void registration(ServiceUserDto user);

    TokenDTO loginAccount(String username, String password);
}
