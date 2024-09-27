package com.music.admin.security.config;

import com.music.admin.security.provider.JwtAuthenticationProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableWebSecurity
@Profile({"prod", "dev", "local"})
@RequiredArgsConstructor
public class SecurityConfiguration {

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http,
            JwtAuthenticationProvider jwtAuthenticationProvider,
            SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> jwtFilterConfigurer
    ) throws Exception {
        http.formLogin().disable();
        http.cors().configurationSource(corsConfigurationSource());
        http.httpBasic().disable();
        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(STATELESS);

        http.authorizeRequests()
                .requestMatchers("/auth/login").permitAll()
                .anyRequest().authenticated();

        http.authenticationProvider(jwtAuthenticationProvider);
        http.apply(jwtFilterConfigurer);

        http.exceptionHandling()
                .authenticationEntryPoint(new HttpStatusEntryPoint(FORBIDDEN));

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowCredentials(true);
        configuration.setAllowedOrigins(List.of("http://localhost:63343/", "http://localhost:3000"));
        configuration.setAllowedMethods(List.of("HEAD", "GET", "POST", "PUT", "OPTIONS", "DELETE", "PATCH"));
        configuration.addAllowedHeader("*");

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }
}
