package com.cdpo.techservice.mapper;

import com.cdpo.techservice.dto.ServiceUserDto;
import com.cdpo.techservice.model.ServiceUser;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface ServiceUserMapper {
    ServiceUser toEntity(ServiceUserDto serviceUserDto);

    ServiceUserDto toDto(ServiceUser serviceUser);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    ServiceUser partialUpdate(ServiceUserDto serviceUserDto, @MappingTarget ServiceUser serviceUser);
}