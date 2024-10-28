package com.cdpo.techservice.service;

import com.cdpo.techservice.dto.ServiceUserDto;
import com.cdpo.techservice.dto.TokenDTO;
import com.cdpo.techservice.exception.AuthenticationException;
import com.cdpo.techservice.mapper.ServiceUserMapper;
import com.cdpo.techservice.model.RoleType;
import com.cdpo.techservice.model.ServiceUser;
import com.cdpo.techservice.model.UserRole;
import com.cdpo.techservice.repository.IServiceUserRepository;
import com.cdpo.techservice.repository.IServiceUserRoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ServiceAuthentication implements IServiceAuthentication {
    public static final String ALREADY_TAKEN = "Username %s is already taken";
    private final ServiceUserMapper serviceUserMapper;
    private final IServiceUserRepository serviceUserRepository;
    private final IServiceUserRoleRepository serviceUserRoleRepository;
    private final IJWTSecurityService jwtSecurityService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    @Override
    public void registration(ServiceUserDto userDto) {
        checkIsNotExist(userDto);
        ServiceUser user = serviceUserMapper.toEntity(userDto);
        fillRole(user);
        fillPassword(user);
        serviceUserRepository.save(user);
    }

    @Override
    public TokenDTO loginAccount(String username, String password) {
        Authentication authentication = authenticate(username, password);
        return generateToken(
                (UserDetails) authentication.getPrincipal(),
                serviceUserRepository.findByUsername(username)
                        .map(ServiceUser::getUserRole)
                        .map(UserRole::getRoleType).orElse(RoleType.ROLE_USER)
        );
    }

    private void fillRole(ServiceUser user) {
        serviceUserRoleRepository.findByRoleType(RoleType.ROLE_USER)
                .ifPresentOrElse(user::setUserRole, () -> user.setUserRole(createRole()));
    }

    private UserRole createRole() {
        UserRole userRole = new UserRole();
        userRole.setRoleType(RoleType.ROLE_USER);
        serviceUserRoleRepository.save(userRole);
        return userRole;
    }

    private void fillPassword(ServiceUser user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
    }

    private void checkIsNotExist(ServiceUserDto userDto) {
        if (serviceUserRepository.existsByUsername(userDto.username()))
            throw new AuthenticationException(String.format(ALREADY_TAKEN, userDto.username()));
    }

    private Authentication authenticate(String username, String password) {
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, password);
        Authentication authentication = authenticationManager.authenticate(authToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return authentication;
    }

    private TokenDTO generateToken(UserDetails userDetails, RoleType roleType) {
        return jwtSecurityService.generateJWT(userDetails, roleType);
    }
}
