package com.cdpo.techservice.service;

import com.cdpo.techservice.dto.ServiceUserDto;
import com.cdpo.techservice.dto.ServiceUserUpdateDto;
import com.cdpo.techservice.dto.TokenDTO;
import com.cdpo.techservice.exception.AuthenticationException;
import com.cdpo.techservice.exception.UserNotFoundException;
import com.cdpo.techservice.mapper.ServiceUserMapper;
import com.cdpo.techservice.model.RoleType;
import com.cdpo.techservice.model.ServiceUser;
import com.cdpo.techservice.model.UserRole;
import com.cdpo.techservice.repository.IServiceUserRepository;
import com.cdpo.techservice.repository.IServiceUserRoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ServiceAuthentication implements IServiceAuthentication {
    public static final String ALREADY_TAKEN = "Username %s is already taken";
    public static final String NOT_ALLOWED_TO_CHANGE_NOT_YOUR_PROFILE = "Not allowed to change not your profile";
    private final ServiceUserMapper serviceUserMapper;
    private final IServiceUserRepository serviceUserRepository;
    private final IServiceUserRoleRepository serviceUserRoleRepository;
    private final IJWTSecurityService jwtSecurityService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    @Override
    public void registration(ServiceUserDto userDto) {
        registration(userDto, RoleType.ROLE_USER);
    }

    @Override
    public void registration(ServiceUserDto userDto, RoleType roleType) {
        checkIsNotExist(userDto);
        ServiceUser user = serviceUserMapper.toEntity(userDto);
        fillRole(user, roleType);
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

    @Override
    public ServiceUserDto updateProfile(ServiceUserUpdateDto serviceUserUpdateDto, String username) {
        ServiceUser applyingUser = serviceUserRepository.findByUsername(username).orElseThrow(UserNotFoundException::new);
        if (applyingUser.getUserRole().getRoleType() == RoleType.ROLE_SUPER_USER) {
            applyingUser = serviceUserRepository
                    .findByUsername(serviceUserUpdateDto.username())
                    .orElseThrow(UserNotFoundException::new);
        } else if (!serviceUserUpdateDto.username().equals(username)) {
                throw new AuthenticationException(HttpStatus.FORBIDDEN, NOT_ALLOWED_TO_CHANGE_NOT_YOUR_PROFILE);
        }
        ServiceUser merged = serviceUserMapper.merge(serviceUserUpdateDto, applyingUser, passwordEncoder);
        serviceUserRepository.save(merged);
        return serviceUserMapper.toDto(merged);
    }

    private void fillRole(ServiceUser user, RoleType roleType) {
        serviceUserRoleRepository.findByRoleType(roleType)
                .ifPresentOrElse(user::setUserRole, () -> user.setUserRole(createUserRole(roleType)));
    }

    private UserRole createUserRole(RoleType roleType) {
        UserRole userRole = new UserRole();
        userRole.setRoleType(roleType);
        serviceUserRoleRepository.save(userRole);
        return userRole;
    }

    private void fillPassword(ServiceUser user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
    }

    private void checkIsNotExist(ServiceUserDto userDto) {
        if (serviceUserRepository.existsByUsername(userDto.username()))
            throw new AuthenticationException(HttpStatus.FORBIDDEN, String.format(ALREADY_TAKEN, userDto.username()));
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
