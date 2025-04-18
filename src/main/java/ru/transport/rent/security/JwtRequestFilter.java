package ru.transport.rent.security;

import java.io.IOException;
import java.util.Optional;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;


@Component
@RequiredArgsConstructor
public class JwtRequestFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsServiceImpl userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");
        Optional<String> username = extractUsername(authHeader);
        if (username.isPresent()) {
            try {
                setAuthenticatedAs(username.get());
            } catch (Exception e) {
                logger.error(e.getMessage());
            }
        }

        filterChain.doFilter(request, response);
    }

    private Optional<String> extractUsername(String header) {
        if (header == null || header.isBlank()) {
            return Optional.empty();
        }

        return jwtService.validateTokenAndRetrieveClaim(
                header.substring(7)
        );
    }

    private void setAuthenticatedAs(String username) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                userDetails, userDetails.getPassword(),
                userDetails.getAuthorities()
        );

        SecurityContextHolder.getContext()
                .setAuthentication(authenticationToken);
    }

}
