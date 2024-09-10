package com.masters.controller;

import com.masters.entity.DefaultDuration;
import com.masters.service.DefaultDurationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/master/defaultDuration")
public class DefaultDurationController {
    @Autowired
    private DefaultDurationService defaultDurationService;
    @GetMapping
    public List<DefaultDuration> getAll(){
        return defaultDurationService.getAll();
    }

    @GetMapping("/get/{id}")
    public DefaultDuration getById(@PathVariable Long id){
        return defaultDurationService.getById(id);
    }
    @PostMapping("/create")
    public DefaultDuration createDuration(@RequestBody DefaultDuration defaultDuration){
        return defaultDurationService.createDuration(defaultDuration);
    }
    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id){
        defaultDurationService.deleteById(id);
    }
    @PutMapping("/update/{id}")
    public DefaultDuration updateRecordById(@PathVariable Long id,@RequestBody DefaultDuration defaultDuration){
      return defaultDurationService.updateRecordById(id,defaultDuration);
    }

}
