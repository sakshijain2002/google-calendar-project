package com.settings.controller;


import com.settings.entity.Settings;
import com.settings.service.SettingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/settings")
public class SettingsController {
    @Autowired
    private SettingsService settingsService;

//    @GetMapping
//    public List<Settings> getAll() {
//        return settingsService.getAll();
//    }

    @GetMapping
    public Optional<Settings> getTask(@RequestHeader("Authorization") String token) {
        return settingsService.getAll(token);
    }

    @GetMapping("/get/{id}")
    public Settings getById(@PathVariable Long id) {
        return settingsService.getById(id);
    }

    @GetMapping("/user/get/{userId}")
    public Optional<Settings> getSettingsForUser(@PathVariable Integer userId) {
        return settingsService.getSettingsForUser(userId);
    }

    @PostMapping("create/{userId}")
    public Settings create(@PathVariable Integer userId, @RequestBody Settings settings) {
        return settingsService.saveSettings(userId, settings);
    }

    @PostMapping("/create-settings")
    public Settings save(@RequestBody Settings settings,@RequestHeader("Authorization") String authorizationHeader){
        String token = authorizationHeader.replace("Bearer","");
        return settingsService.createOrUpdateSettings(settings,token);
    }



    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        settingsService.deleteById(id);
    }

    @PutMapping("/update")
    public Settings updateRecordById(@RequestBody Settings settings, @RequestHeader("Authorization") String authorizationHeader) {
        String token = authorizationHeader.replace("Bearer","");
        return settingsService.updateRecordById(token,settings);
    }

}
