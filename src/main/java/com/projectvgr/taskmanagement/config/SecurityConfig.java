package com.projectvgr.taskmanagement.config;

import com.projectvgr.taskmanagement.service.UserDetailsServiceImpl;
import jakarta.servlet.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import java.io.IOException;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{
        httpSecurity.authorizeHttpRequests(
                auth ->{
                    auth
                            .requestMatchers("/user/register").permitAll()
                            .requestMatchers("/user/users").hasAnyAuthority("ADMIN")
                            .anyRequest().authenticated();
                });
        httpSecurity.addFilterBefore(
                new HeaderFilter(), BasicAuthenticationFilter.class
        );
        httpSecurity.sessionManagement(
                session -> {
                    session.sessionCreationPolicy(SessionCreationPolicy.STATELESS); //otherwise create/generate csrf token
                });
        httpSecurity
                .csrf((csrf) -> csrf.disable());
        httpSecurity.httpBasic(Customizer.withDefaults());
        return httpSecurity.build();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {

        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;

    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
