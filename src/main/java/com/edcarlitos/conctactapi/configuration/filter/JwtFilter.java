package com.edcarlitos.conctactapi.configuration.filter;

import com.edcarlitos.conctactapi.repository.UserRepository;
import com.edcarlitos.conctactapi.service.IUserDetailsService;
import com.edcarlitos.conctactapi.service.JwtService;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@AllArgsConstructor
@Service
public class JwtFilter extends OncePerRequestFilter {

    private JwtService jwtService;
    private IUserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String headerAuthorization = request.getHeader("Authorization");

        if(headerAuthorization != null && headerAuthorization.startsWith("Bearer ")) {
            String token = headerAuthorization.substring(7);

            if(jwtService.validateToken(token)) {

                String email = jwtService.getEmail(token);
                UserDetails userDetails = userDetailsService.loadUserByUsername(email);
                var tokenAuth = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities());


                SecurityContextHolder.getContext().setAuthentication(tokenAuth);

            }

        }
            filterChain.doFilter(request, response);
    }
}
