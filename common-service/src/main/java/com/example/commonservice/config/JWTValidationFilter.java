package com.example.commonservice.config;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.example.commonservice.security.JWTUtil;
import com.example.commonservice.service.impl.UserSecurityDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JWTValidationFilter extends OncePerRequestFilter {
    private final String TOKEN_PREFIX = "Bearer ";
    private final Integer TOKEN_START_POSITION = 7;
    private final String SUBJECT = "User details";
    private final String ISSUER = "Matthew";
    private final JWTUtil jwtUtil;
    private final UserSecurityDetailsService userSecurityDetailsService;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (authHeader != null && !authHeader.isBlank() && authHeader.startsWith(TOKEN_PREFIX)) {
            String jwt = authHeader.substring(TOKEN_START_POSITION);
            try {
                jwtUtil.validateToken(jwt);
                String username = jwtUtil.getClaimFromToken(jwt, "login");
                UserDetails userDetails = userSecurityDetailsService.loadUserByUsername(username);

                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(userDetails,
                                userDetails.getPassword(),
                                userDetails.getAuthorities());

                if (SecurityContextHolder.getContext().getAuthentication() == null) {
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            } catch (JWTVerificationException exc) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            }
        }
        filterChain.doFilter(request, response);
    }
}
