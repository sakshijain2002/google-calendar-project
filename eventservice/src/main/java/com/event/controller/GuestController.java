package com.event.controller;

import com.event.entity.Guest;
import com.event.service.GuestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/guest")
public class GuestController {

    @Autowired
    private GuestService guestService;

    @GetMapping
    public List<Guest> getAll(){
        return guestService.getAll();
    }

    @GetMapping("/{id}")
    public Guest getById(@PathVariable Long id){
        return guestService.getById(id);
    }

    @PostMapping("/save")
    public List<Guest> saveGuest(@RequestBody List<Guest> guest){
        return guestService.saveAll(guest);
    }
    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id){
        guestService.deleteById(id);
    }

    @PutMapping("/update/{id}")
    public Guest updateRecordById(@PathVariable Long id,@RequestBody Guest guest){
        return guestService.updateRecordById(id,guest);
    }

}
