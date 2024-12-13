package com.masters.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final LocaleChangeInterceptor localeChangeInterceptor;

    public WebConfig(LocaleChangeInterceptor localeChangeInterceptor) {
        this.localeChangeInterceptor = localeChangeInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(localeChangeInterceptor); // Registering the interceptor
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/masters/**")  // Allow all paths
                .allowedOrigins("http://localhost:3000","http://192.168.217.218:3000","http://192.168.180.161:3000")  // Allow specific origins
                .allowedMethods("GET", "POST", "PUT", "DELETE","OPTIONS")  // Allow specific HTTP methods
                .allowedHeaders("Authorization","Content-Type")  // Allow all headers
                .allowCredentials(true)
                .maxAge(3600);  // Cache preflight response for 1 hour
    }


}


