package com.gateway.filter;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class RoleBasedAccessFilter extends AbstractGatewayFilterFactory<RoleBasedAccessFilter.Config> {

    public RoleBasedAccessFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            String rolesHeader = exchange.getRequest().getHeaders().getFirst("roles");

            // Log the roles header for debugging
            System.out.println("Roles Header: " + rolesHeader);

            if (rolesHeader != null) {
                List<String> userRoles = Arrays.asList(rolesHeader.split(","));
                boolean hasAccess = userRoles.stream()
                        .anyMatch(role -> config.requiredRoles.contains(role));

                if (!hasAccess) {
                    exchange.getResponse().setStatusCode(HttpStatus.FORBIDDEN);
                    return exchange.getResponse().setComplete();
                }
            } else {
                // If roles header is missing, deny access
                exchange.getResponse().setStatusCode(HttpStatus.FORBIDDEN);
                return exchange.getResponse().setComplete();
            }
            return chain.filter(exchange);
        };
    }

    public static class Config {
        private List<String> requiredRoles;

        // Constructor, Getters, and Setters
        public Config(List<String> requiredRoles) {
            this.requiredRoles = requiredRoles;
        }
    }
}