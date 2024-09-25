package com.masters.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/master/**")  // Allow all paths
                .allowedOrigins("http://localhost:3000")  // Allow specific origins
                .allowedMethods("GET", "POST", "PUT", "DELETE","OPTIONS")  // Allow specific HTTP methods
                .allowedHeaders("Authorization","Content-Type")  // Allow all headers
                .allowCredentials(true)
                .maxAge(3600);  // Cache preflight response for 1 hour
    }
}


