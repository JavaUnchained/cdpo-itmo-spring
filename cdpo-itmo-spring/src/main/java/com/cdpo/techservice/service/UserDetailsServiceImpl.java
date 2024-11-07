package com.cdpo.techservice.service;

import com.cdpo.techservice.exception.AuthenticationException;
import com.cdpo.techservice.model.ServiceUser;
import com.cdpo.techservice.repository.IServiceUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    public static final String USER_NOT_FOUNDED = "User not founded";
    private final IServiceUserRepository serviceUserRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        ServiceUser user = serviceUserRepository.findByUsername(username)
                .orElseThrow(() -> new AuthenticationException(HttpStatus.UNAUTHORIZED, USER_NOT_FOUNDED));
        return new User(username, user.getPassword(), getAuthorities(user));
    }

    private static Set<SimpleGrantedAuthority> getAuthorities(ServiceUser user) {
        return Set.of(new SimpleGrantedAuthority(user.getUserRole().getRoleType().name()));
    }

}
