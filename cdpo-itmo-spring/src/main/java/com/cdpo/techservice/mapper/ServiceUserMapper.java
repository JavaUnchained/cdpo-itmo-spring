package com.cdpo.techservice.mapper;

import com.cdpo.techservice.dto.ServiceUserDto;
import com.cdpo.techservice.dto.ServiceUserUpdateDto;
import com.cdpo.techservice.model.ServiceUser;
import org.mapstruct.*;
import org.springframework.security.crypto.password.PasswordEncoder;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface ServiceUserMapper {
    ServiceUser toEntity(ServiceUserDto serviceUserDto);

    ServiceUserDto toDto(ServiceUser serviceUser);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    ServiceUser partialUpdate(ServiceUserDto serviceUserDto, @MappingTarget ServiceUser serviceUser);

    default ServiceUser merge(ServiceUserUpdateDto updateDto, ServiceUser serviceUser, PasswordEncoder passwordEncoder) {
        if (updateDto.email() != null) {
            serviceUser.setEmail(updateDto.email());
        }
        if(updateDto.firstName() != null) {
            serviceUser.setFirstName(updateDto.firstName());
        }
        if(updateDto.lastName() != null) {
            serviceUser.setLastName(updateDto.lastName());
        }
        if(updateDto.password() != null) {
            serviceUser.setPassword(passwordEncoder.encode(updateDto.password()));
        }
        return serviceUser;
    }
}