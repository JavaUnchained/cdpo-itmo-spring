package com.cdpo.techservice.service;

import com.cdpo.techservice.dto.ServiceUserDto;
import com.cdpo.techservice.dto.ServiceUserUpdateDto;
import com.cdpo.techservice.dto.TokenDTO;
import com.cdpo.techservice.model.RoleType;

public interface IServiceAuthentication {
    void registration(ServiceUserDto user);

    void registration(ServiceUserDto userDto, RoleType roleType);

    TokenDTO loginAccount(String username, String password);

    ServiceUserDto updateProfile(ServiceUserUpdateDto serviceUserUpdateDto);
}
