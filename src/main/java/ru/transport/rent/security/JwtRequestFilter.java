package ru.transport.rent.security;

import java.io.IOException;
import java.util.Optional;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;


/**
 * Создания кастомного фильтра для jwt.
 */
@Component
@RequiredArgsConstructor
public class JwtRequestFilter extends OncePerRequestFilter {

    public static final int BEGIN_INDEX = 7;
    private final JwtService jwtService;
    private final UserDetailsServiceImpl userDetailsService;


    @Override
    protected void doFilterInternal(final HttpServletRequest request,
            final HttpServletResponse response,
            final FilterChain filterChain) throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization");
        final Optional<String> username = extractUsername(authHeader);
        if (username.isPresent()) {
            try {
                setAuthenticatedAs(username.get());
            } catch (UsernameNotFoundException e) {
                if (logger.isErrorEnabled()) {
                    logger.error(e.getMessage());
                }
            }
        }

        filterChain.doFilter(request, response);
    }


    private Optional<String> extractUsername(final String header) {
        if (header == null || header.isBlank()) {
            return Optional.empty();
        }

        return jwtService.validateTokenAndRetrieveClaim(
                header.substring(BEGIN_INDEX)
        );
    }


    private void setAuthenticatedAs(final String username) {
        final UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        final UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                userDetails, userDetails.getPassword(),
                userDetails.getAuthorities()
        );

        SecurityContextHolder.getContext()
                .setAuthentication(authenticationToken);
    }

}
