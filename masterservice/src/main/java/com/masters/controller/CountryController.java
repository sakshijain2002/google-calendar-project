package com.masters.controller;

import com.masters.entity.Country;
import com.masters.service.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/master/country")
public class CountryController {
    @Autowired
    private CountryService countryService;
    @GetMapping
    public List<Country> getAll(){
        return countryService.getAll();
    }

    @GetMapping("/get/{id}")
    public Country getById(@PathVariable Long id){
        return countryService.getById(id);
    }
    @PostMapping("/create")
    public Country createCountry(@RequestBody Country country){
        return countryService.createCountry(country);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id){
        countryService.deleteById(id);
    }
    @PutMapping("/update/{id}")
    public Country updateRecordById(@PathVariable Long id,@RequestBody Country country){
        return countryService.updateRecordById(id,country);
    }


}
