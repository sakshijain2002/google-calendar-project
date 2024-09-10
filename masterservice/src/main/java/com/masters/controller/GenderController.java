package com.masters.controller;

import com.masters.entity.Gender;
import com.masters.service.GenderService;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/gender")
public class GenderController {

    @Autowired
    private GenderService genderService;
    @GetMapping
    public List<Gender> getAll(){
        return genderService.getAll();
    }
    @GetMapping("/{id}")
    public Gender getById(@PathVariable Integer id){
        return genderService.getById(id);
    }

    @PostMapping("/create")
    public Gender save(@RequestBody Gender gender){
        return genderService.save(gender);
    }
    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Integer id){
        genderService.deleteById(id);
    }
    @PutMapping("/update/{id}")
    public Gender updateRecordById(@PathVariable Integer id,@RequestBody Gender gender){
       return genderService.updateRecordById(id,gender);
    }


}
