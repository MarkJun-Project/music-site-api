package com.music.core.security.provider;


import com.music.core.security.component.JwtComponent;
import com.music.core.security.user.DefaultAuthenticationUser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

import static com.music.core.security.component.JwtType.ACCESS;


@Component
@RequiredArgsConstructor
public class JwtAuthenticationProvider implements AuthenticationProvider {

    private final JwtComponent jwtComponent;

    @Override
    public Authentication authenticate(Authentication authentication) {
        String token = (String) authentication.getPrincipal();

        jwtComponent.validate(token, ACCESS);

        Long id = jwtComponent.getId(token);

        return new UsernamePasswordAuthenticationToken(new DefaultAuthenticationUser(id), token, new ArrayList<>());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}

