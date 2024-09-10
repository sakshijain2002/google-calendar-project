package com.masters.controller;


import com.masters.entity.Visibility;
import com.masters.service.VisibilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/master/visibility")
public class VisibilityController {

    @Autowired
    private VisibilityService visibilityService;

    @GetMapping
    public List<Visibility> getAll(){
        return visibilityService.getAll();
    }

    @GetMapping("/get/{id}")
    public Visibility getById(@PathVariable Long id){
        return visibilityService.getById(id);
    }

    @PostMapping("/create")
    public Visibility save(@RequestBody Visibility visibility){
        return visibilityService.create(visibility);
    }

    @DeleteMapping("/{id}")
    public void deleteById(Long id){
        visibilityService.deleteById(id);
    }

    @PutMapping("/update/{id}")
    public Visibility updateRecordById(@PathVariable Long id,@RequestBody Visibility visibility){
        return visibilityService.updateRecordById(id,visibility);
    }

}
