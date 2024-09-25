package com.gateway.filter;

import com.gateway.util.JwtUtil;

import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> implements WebFilter {

    @Autowired
    private RouteValidator validator;

    @Autowired
    private JwtUtil jwtUtil;

    public AuthenticationFilter() {
        super(Config.class);
    }

//    @Override
//    public GatewayFilter apply(Config config) {
//        return ((exchange, chain) -> {
//            if (validator.isSecured.test(exchange.getRequest())) {
//                //header contains token or not
//                if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
//                    throw new RuntimeException("missing authorization header");
//                }
//
//                String authHeader = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
//                if (authHeader != null && authHeader.startsWith("Bearer ")) {
//                    authHeader = authHeader.substring(7);
//                }
//                try {
////                    //REST call to AUTH service
////                    template.getForObject("http://IDENTITY-SERVICE//validate?token" + authHeader, String.class);
//                    jwtUtil.validateToken(authHeader);
//
//                } catch (Exception e) {
//                    System.out.println("invalid access...!");
//                    throw new RuntimeException("un authorized access to application");
//                }
//            }
//            return chain.filter(exchange);
//        });
//    }
@Override
public GatewayFilter apply(Config config) {
    return (exchange, chain) -> {
        String authHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }

        String token = authHeader.substring(7);
//        Claims claims = jwtUtil.extractAllClaims(token);

        // Log claims for debugging
//        System.out.println("JWT Claims: " + claims);
//
//        if (claims == null) {
//            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
//            return exchange.getResponse().setComplete();
//        }
//
//        // Extract roles from the JWT claims
//        List<String> roles = claims.get("roles", List.class);
//
//        // Log the extracted roles
//        System.out.println("Extracted Roles: " + roles);
//
//        if (roles == null || roles.isEmpty()) {
//            exchange.getResponse().setStatusCode(HttpStatus.FORBIDDEN);
//            return exchange.getResponse().setComplete();
//        }

        // Add roles to the request header for downstream services to check
//        exchange.getRequest().mutate().header("roles", String.join(",", roles)).build();

        return chain.filter(exchange);
    };
}


    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        // Bypass OPTIONS requests



        if (validator.isSecured.test(exchange.getRequest())) {
            if (HttpMethod.OPTIONS.equals(exchange.getRequest().getMethod())) {
                return chain.filter(exchange);
            }
            // Check if the Authorization header is present
            if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                return Mono.error(new RuntimeException("Missing authorization header"));
            }

            String authHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                authHeader = authHeader.substring(7); // Remove "Bearer " prefix
//                Claims claims = jwtUtil.extractAllClaims(authHeader);
            } else {
                return Mono.error(new RuntimeException("Invalid authorization header format"));
            }

            try {
                // Validate the JWT token
                jwtUtil.validateToken(authHeader);
            } catch (Exception e) {
                return Mono.error(new RuntimeException("Unauthorized access to application"));
            }
        }

        // Continue the filter chain
        return chain.filter(exchange);
    }

    public static class Config {

        public <E> Config() {
        }
    }
}