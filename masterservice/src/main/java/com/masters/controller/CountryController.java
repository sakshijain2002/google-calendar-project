package com.masters.controller;

import com.masters.entity.Country;
import com.masters.service.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/master/country")
@CrossOrigin
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

//    @DeleteMapping("/delete/{id}/{email}")
//    public ResponseEntity<String> deleteById(@PathVariable Long id, @PathVariable String email) {
//        try {
//            countryService.deleteById(id, email);
//            return ResponseEntity.ok("Deletion successful.");
//        } catch (RuntimeException e) {
//            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access denied: You do not have permission to delete.");
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while attempting to delete.");
//        }
//    }

    @DeleteMapping("/delete/{id}")
    public void deleteById(@PathVariable Long id){
        countryService.deleteById(id);
    }
    @PutMapping("/update/{id}")
    public Country updateRecordById(@PathVariable Long id,@RequestBody Country country){
        return countryService.updateRecordById(id,country);
    }


}
