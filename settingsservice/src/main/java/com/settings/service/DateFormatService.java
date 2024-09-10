package com.settings.service;

import com.settings.model.DateFormatModel;
import com.settings.model.TimeZoneModel;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(url="http://localhost:8086",value = "date-format")
public interface DateFormatService {
    @GetMapping("/master/dateFormat/get/{id}")
    DateFormatModel getDateFormatById(@PathVariable Integer id);
}
