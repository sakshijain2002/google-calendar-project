package com.gateway.configuration;

import com.gateway.filter.AuthenticationFilter;
import io.netty.resolver.DefaultAddressResolverGroup;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.config.web.server.ServerHttpSecurity;

import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.web.client.RestTemplate;
import reactor.netty.http.client.HttpClient;

@Configuration
public class AppConfig {
    private AuthenticationFilter authFilter;

    @Bean
    public RestTemplate template(){
        return new RestTemplate();
    }
    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        http
                .csrf(csrf -> csrf.disable());

        return http.build();
    }
}
