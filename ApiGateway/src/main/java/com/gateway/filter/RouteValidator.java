package com.gateway.filter;


import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import java.util.List;
import java.util.function.Predicate;


@Component
public class RouteValidator {

    private final AntPathMatcher pathMatcher = new AntPathMatcher();
    public static final List<String> openApiEndpoints = List.of(
            "/control",
            "/auth/register",
            "/auth/token",
            "/auth/refreshToken",
            "/auth/validate",
            "/masters/**",
            "/auth/search/{email}",
            "/event/trashed",
            "masters/country",
            "masters/language",
            "masters/timezone",
            "masters/date-format",
            "/auth/admin/users",
            "/auth/admin/change-role",
            "/auth/admin/delete/{email}",
            "/eureka"

    );

//    public Predicate<ServerHttpRequest> isSecured =
//            request -> openApiEndpoints
//                    .stream()
//                    .noneMatch(uri -> request.getURI().getPath().contains(uri));
public Predicate<ServerHttpRequest> isSecured =
        request -> openApiEndpoints
                .stream()
                .noneMatch(uri -> pathMatcher.match(uri, request.getURI().getPath()));

}