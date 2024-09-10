package com.masters.controller;

import com.masters.entity.GuestPermission;
import com.masters.service.GuestPermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/master/guestPermission")
public class GuestPermissionController {
     @Autowired
     private GuestPermissionService guestPermissionService;
     @GetMapping
     public List<GuestPermission> getAll(){
         return guestPermissionService.getAll();
     }
     @PostMapping("/create")
     public GuestPermission savePermission(@RequestBody GuestPermission guestPermission){
         return guestPermissionService.saveGuestPermission(guestPermission);
     }
     @DeleteMapping("/{id}")
     public void deleteById(@PathVariable Long id){
         guestPermissionService.deleteById(id);
     }

     @PutMapping("/update/{id}")
     public GuestPermission updateRecordById(@PathVariable Long id ,@RequestBody GuestPermission guestPermission){
        return guestPermissionService.updateRecordById(id,guestPermission);
     }

}
