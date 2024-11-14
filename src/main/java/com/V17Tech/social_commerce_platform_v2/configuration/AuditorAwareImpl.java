package com.V17Tech.social_commerce_platform_v2.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import java.util.Optional;

import static java.util.Objects.isNull;

@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AuditorAwareImpl implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {
        return Optional.ofNullable(extractUsernameFromAuthentication());
    }

    public static String extractUsernameFromAuthentication() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username;
        if ( isNull( authentication ) ) {
            return null;
        }
        if ( authentication instanceof JwtAuthenticationToken) {
            JwtAuthenticationToken token = (JwtAuthenticationToken) authentication;
            username = (String) ( token ).getTokenAttributes().get( "preferred_username" );
        } else {
            username = authentication.getName();
        }
        return username;
    }
}
