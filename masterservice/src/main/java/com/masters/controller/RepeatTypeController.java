package com.masters.controller;

import com.masters.entity.RepeatType;
import com.masters.service.RepeatTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/master/repeatType")
public class RepeatTypeController {
    @Autowired
    private RepeatTypeService repeatTypeService;

    @GetMapping
    public List<RepeatType> getAll(){
        return repeatTypeService.getAll();
    }
    @GetMapping("/get/{id}")
    public RepeatType getById(@PathVariable Long id){
        return repeatTypeService.getById(id);
    }
    @PostMapping("/create")
    public RepeatType save(@RequestBody RepeatType type){
        return repeatTypeService.save(type);
    }
    @PutMapping("/update/{id}")
    public RepeatType updateRecordById(@PathVariable Long id,@RequestBody RepeatType type){
       return repeatTypeService.updateRecordById(id,type);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteById(@PathVariable Long id){
        repeatTypeService.deleteById(id);
    }
}
