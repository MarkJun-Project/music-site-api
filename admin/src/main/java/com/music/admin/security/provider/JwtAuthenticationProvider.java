package com.music.admin.security.provider;

import com.music.admin.security.component.JwtComponent;
import com.music.admin.security.user.DefaultAuthenticationUser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

import static com.music.admin.security.component.JwtType.ACCESS;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationProvider implements AuthenticationProvider {

    private final JwtComponent jwtComponent;

    @Override
    public Authentication authenticate(Authentication authentication) {
        String token = (String) authentication.getPrincipal();

        jwtComponent.validate(token, ACCESS);

        String id = jwtComponent.getId(token);

        return new UsernamePasswordAuthenticationToken(new DefaultAuthenticationUser(id), token, new ArrayList<>());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}

