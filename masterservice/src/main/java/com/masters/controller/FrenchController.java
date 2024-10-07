package com.masters.controller;

import com.masters.entity.French;
import com.masters.service.FrenchService;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequestMapping("/french")
@RestController
public class FrenchController {

    @Autowired
    private FrenchService frenchService;

    @GetMapping
    public List<French> getAll(){
        return frenchService.getAll();
    }

    @GetMapping("/{id}")
    public French getById(@PathVariable Integer id){
        return frenchService.getById(id);
    }
    @PostMapping("/create")
    public String save(@RequestBody Map<String,String> french){
        frenchService.saveTranslations(french);
        return "created successfully";
    }

}
