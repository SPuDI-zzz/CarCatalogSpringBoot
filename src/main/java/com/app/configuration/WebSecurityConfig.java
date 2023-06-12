package com.app.configuration;

import com.app.security.JwtRequestFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig {
    private static final String AUTH_ALL = "/api/auth/**";
    private static final String PROFILES = "/api/profiles";
    private static final String PROFILES_ALL = "/api/profiles/**";
    private static final String USER = "USER";
    public static final String VEHICLES_ALL = "/api/vehicles/**";
    public static final String VEHICLES = "/api/vehicles";
    public static final String IP_ADDRESS = "hasIpAddress('127.0.0.1')";

    private final JwtRequestFilter jwtRequestFilter;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .authorizeRequests()
                .antMatchers(AUTH_ALL).permitAll()
                .antMatchers(HttpMethod.POST, PROFILES).permitAll()
                .antMatchers(VEHICLES_ALL, PROFILES_ALL).hasRole(USER)
                .antMatchers(HttpMethod.GET, PROFILES, VEHICLES).access(IP_ADDRESS)
                .and()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }
}
