package com.masters.controller;

import com.masters.entity.Language;
import com.masters.service.LanguageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/master/language")
public class LanguageController {
    @Autowired
    private LanguageService languageService;

    @GetMapping
    public List<Language> getAll(){
        return languageService.getAll();
    }

    @GetMapping("/get/{id}")
    public Language getById(@PathVariable Long id){
        return languageService.getById(id);
    }
    @PostMapping("/create")
    public Language createLanguage(@RequestBody Language language){
        return languageService.createLanguage(language);
    }

    @PutMapping("/update/{id}")
    public Language updateRecordById(@PathVariable Long id, @RequestBody Language language){
        return languageService.updateRecordById(id,language);
    }
    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id){
        languageService.deleteById(id);
    }

}
