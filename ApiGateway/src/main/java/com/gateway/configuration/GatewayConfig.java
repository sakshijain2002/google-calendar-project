//package com.gateway.configuration;
//
//import com.gateway.filter.AuthenticationFilter;
//import com.gateway.filter.RoleBasedAccessFilter;
//import org.springframework.cloud.gateway.route.RouteLocator;
//import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.HttpMethod;
//
//import java.util.List;
//
//@Configuration
//public class GatewayConfig {
//
//    private final AuthenticationFilter jwtAuthenticationFilter;
//    private final RoleBasedAccessFilter roleBasedAccessFilter;
//
//    public GatewayConfig(AuthenticationFilter jwtAuthenticationFilter, RoleBasedAccessFilter roleBasedAccessFilter) {
//        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
//        this.roleBasedAccessFilter = roleBasedAccessFilter;
//    }
//
//    @Bean
//    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
//        return builder.routes()
//                .route("masterservice", r -> r.path("/master/**")
//                        .and().method(HttpMethod.DELETE, HttpMethod.PUT)
//                        .filters(f -> f.filter(jwtAuthenticationFilter.apply(new AuthenticationFilter.Config()))
//                                .filter(roleBasedAccessFilter.apply(new RoleBasedAccessFilter.Config(List.of("ROLE_ADMIN")))))
//                        .uri("http://localhost:8086/masterservice/"))
//                .route("user_service", r -> r.path("/user/**")
//                        .filters(f -> f.filter(jwtAuthenticationFilter.apply(new AuthenticationFilter.Config()))
//                                .filter(roleBasedAccessFilter.apply(new RoleBasedAccessFilter.Config(List.of("ROLE_USER")))))
//                        .uri("http://user-service"))
//                .build();
//    }
//}
//
