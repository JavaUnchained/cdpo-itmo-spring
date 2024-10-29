package com.cdpo.techservice.controller.filter;

import com.cdpo.techservice.service.IJWTSecurityService;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.proc.BadJOSEException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.text.ParseException;

@RequiredArgsConstructor
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private static final String HEADER_AUTHORIZATION = "Authorization";

    private final IJWTSecurityService jwtSecurityService;
    private final UserDetailsService userDetailService;


    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String authHeader = request.getHeader(HEADER_AUTHORIZATION);

        if (isBearer(authHeader)) {
            filterChain.doFilter(request, response);
            return;
        }

        String jwt = authHeader.substring(7);

        String userName = getUserName(jwt);
        if (isNeedAuthenticate(userName)) {
            authenticate(request, userName, jwt);
        }

        filterChain.doFilter(request, response);
    }

    private void authenticate(HttpServletRequest request, String userName, String jwt) throws IOException {
        UserDetails userDetails = userDetailService.loadUserByUsername(userName);
        try {
            if (jwtSecurityService.isTokenValid(jwt, userDetails)) {
                UsernamePasswordAuthenticationToken authToken = createAuthenticationToken(request, userDetails);
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        } catch (BadJOSEException | ParseException | JOSEException e) {
            throw new IOException(e);
        }
    }

    private static UsernamePasswordAuthenticationToken createAuthenticationToken(HttpServletRequest request,
                                                                                 UserDetails userDetails) {
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        return authToken;
    }

    private static boolean isNeedAuthenticate(String userName) {
        return userName != null && SecurityContextHolder.getContext().getAuthentication() == null;
    }

    private String getUserName(String jwt) throws IOException {
        String userName;
        try {
            userName = jwtSecurityService.getSubject(jwt);
        } catch (BadJOSEException | ParseException | JOSEException e) {
            throw new IOException(e);
        }
        return userName;
    }

    private static boolean isBearer(String authHeader) {
        return authHeader == null || !authHeader.startsWith("Bearer");
    }
}