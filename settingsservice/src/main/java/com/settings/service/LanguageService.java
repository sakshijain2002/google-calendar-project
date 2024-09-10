package com.settings.service;

import com.settings.model.CustomView;
import com.settings.model.Language;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(url="http://localhost:8086",value = "language-client")
public interface LanguageService {
    @GetMapping("/master/language/get/{id}")
    Language getById(@PathVariable Long id);
}
