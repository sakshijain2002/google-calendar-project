package com.masters.controller;

import com.masters.entity.CustomView;
import com.masters.service.CustomViewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/master/customView")
public class CustomViewController {
    @Autowired
    private CustomViewService customViewService;

    @GetMapping
    public List<CustomView> getAll(){
        return customViewService.getAll();
    }

    @GetMapping("/get/{id}")
    public CustomView getById(@PathVariable Long id){
        return customViewService.getById(id);
    }

    @PostMapping("/create")
    public CustomView save(@RequestBody CustomView view){
        return customViewService.saveCustomView(view);
    }
    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id){
        customViewService.deleteById(id);
    }

    @PutMapping("/update/{id}")
    public CustomView updateRecordById(@PathVariable Long id ,@RequestBody CustomView view){
        return customViewService.updateRecordById(id,view);
    }

}
