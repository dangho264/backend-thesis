package com.thesis.userservice.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class AuthenticationConfig {

  private final com.thesis.userservice.config.UserAuthenticationEntryPoint userAuthenticationEntryPoint;

  private final com.thesis.userservice.config.UserAuthProvider userAuthProvider;

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http.exceptionHandling().authenticationEntryPoint(userAuthenticationEntryPoint)
            .and()
            .addFilterBefore(new com.thesis.userservice.config.JwtAuthFilter(userAuthProvider), BasicAuthenticationFilter.class)
            .csrf().disable()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .authorizeHttpRequests((request) -> request.antMatchers(HttpMethod.POST, "/api/v1/users/login", "/api/v1/users/register").permitAll()
                    .antMatchers(HttpMethod.GET, "/api/v1/users/validate").permitAll()
                    .anyRequest().authenticated());
    return http.build();

  }
}

