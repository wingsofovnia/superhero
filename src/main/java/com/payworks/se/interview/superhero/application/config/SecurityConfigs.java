package com.payworks.se.interview.superhero.application.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

public class SecurityConfigs {

    @Configuration
    @Profile("dev")
    @EnableWebFluxSecurity
    public static class DevSecurityConfig {

        @Bean
        public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
            return http.csrf().disable()
                .authorizeExchange()
                    .anyExchange().permitAll().and()
                .build();
        }
    }

    @Configuration
    @Profile("prod")
    @EnableWebFluxSecurity
    public static class ProdSecurityConfig {

        @Bean
        public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
            return http.csrf().disable()
                .authorizeExchange()
                    .anyExchange().authenticated().and()
                .httpBasic().and()
                .build();
        }
    }
}
