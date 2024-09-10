package com.masters.controller;

import com.masters.entity.AddInvitation;
import com.masters.service.AddInvitationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/master/addInvitation")
public class AddInvitationController {
    @Autowired
     private AddInvitationService addInvitationService;

    @GetMapping
    public List<AddInvitation> getAll(){
        return addInvitationService.getAll();
    }

    @GetMapping("/get/{id}")
    public Optional<AddInvitation> getById(@PathVariable Long id){
        return addInvitationService.getById(id);
    }

    @PostMapping("/create")
    public AddInvitation saveAddInvitation(@RequestBody AddInvitation addInvitation){
        return addInvitationService.saveInvitation(addInvitation);
    }
    @PutMapping("/update/{id}")
    public AddInvitation updateRecordById(@PathVariable Long id,@RequestBody AddInvitation addInvitation){
        return addInvitationService.updateRecordById(id,addInvitation);
    }
    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id){
        addInvitationService.deleteById(id);
    }
}
