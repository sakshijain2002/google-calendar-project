package com.settings.service;

import com.settings.model.CountryModel;
import com.settings.model.TimeZoneModel;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(url= "http://localhost:8086",value="country-service")
public interface CountryService {
    @GetMapping("/master/country/get/{id}")
    CountryModel getCountryById(@PathVariable Long id);

}
