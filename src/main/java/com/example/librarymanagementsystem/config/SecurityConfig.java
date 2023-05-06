package com.example.librarymanagementsystem.config;

import com.example.librarymanagementsystem.security.JwtAccessDeniedHandler;
import com.example.librarymanagementsystem.security.JwtAuthenticationEntryPoint;
import com.example.librarymanagementsystem.security.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;


    public SecurityConfig(
            JwtAuthenticationFilter jwtAuthenticationFilter,
            JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint,
            JwtAccessDeniedHandler jwtAccessDeniedHandler) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
        this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
        this.jwtAccessDeniedHandler = jwtAccessDeniedHandler;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf().disable()
                .formLogin().disable()
                .httpBasic().disable()
                .cors()
                .and()
                .authorizeHttpRequests(authorize -> authorize
                                .requestMatchers(HttpMethod.POST, "/api/v1/").hasAnyAuthority("LIBRARIAN")
                                .requestMatchers(HttpMethod.PUT, "/api/v1/").hasAnyAuthority("LIBRARIAN")
                                .requestMatchers(HttpMethod.DELETE, "/api/v1/").hasAnyAuthority("LIBRARIAN")
                                .requestMatchers("/api/v1/auth/**").permitAll()
                                .requestMatchers(HttpMethod.GET, "/api/v1/author/**").permitAll()
                                .requestMatchers(HttpMethod.GET, "/api/v1/book/**").permitAll()
                                .requestMatchers(HttpMethod.GET, "/api/v1/category/**").permitAll()
                                .requestMatchers(HttpMethod.GET, "/api/v1/detail/**").permitAll()
                                .requestMatchers(HttpMethod.GET, "/api/v1/publisher/**").permitAll()
                                .requestMatchers(HttpMethod.GET, "/api/v1/transaction/**").permitAll()
                                .anyRequest()
                                .authenticated()
//                        .requestMatchers("/v1/book/**")
                )
                .exceptionHandling()
                .accessDeniedHandler(jwtAccessDeniedHandler)
                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return web -> web.ignoring().requestMatchers(
                "/swagger-ui.html",
                "/swagger-ui/**",
                "/v3/api-docs/**"
        );
    }

}
