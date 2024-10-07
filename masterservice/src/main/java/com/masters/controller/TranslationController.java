package com.masters.controller;

import com.masters.entity.Translation;
import com.masters.service.TranslationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/translation")
public class TranslationController {

    @Autowired
    private TranslationService translationService;

    @GetMapping
    public List<Translation> getAll(){
        return translationService.getAll();
    }

    @GetMapping("/{id}")
    public Translation getById(@PathVariable Integer id){
        return translationService.getById(id);
    }

    @PostMapping("/create")
    public String saveTranslation(@RequestBody Map<String, String> translations){
         translationService.saveTranslations(translations);
         return "translation created successfully";
    }
    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Integer id){
        translationService.deleteById(id);
    }


}
