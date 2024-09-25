package com.masters.controller;

import com.masters.entity.Language;
import com.masters.model.UserModel;
import com.masters.service.LanguageService;
import com.masters.service.UserServiceClient;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;
import java.util.List;

@RestController
@RequestMapping("/master/language")
public class LanguageController {

    @Autowired
   private UserServiceClient userServiceClient;
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

//    @DeleteMapping("/delete/{id}/{email}")
//    public ResponseEntity<String> deleteById(@PathVariable Long id, @PathVariable String email) {
//        try {
//            languageService.deleteById(id, email);
//            return ResponseEntity.ok("Deletion successful.");
//        } catch (RuntimeException e) {
//            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access denied: You do not have permission to delete.");
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while attempting to delete.");
//        }
//    }

    @DeleteMapping("/delete/{id}")
    public void deleteById(@PathVariable Long id){
        languageService.deleteById(id);
    }

}
