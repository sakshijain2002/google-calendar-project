package com.masters.service;

import com.masters.entity.AddInvitation;
import com.masters.repository.AddInvitationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AddInvitationService {
    @Autowired
    private AddInvitationRepository addInvitationRepository;

    public List<AddInvitation> getAll(){
        return addInvitationRepository.findAll();
    }
    public Optional<AddInvitation> getById(Long Id){
        return addInvitationRepository.findById(Id);
    }
    public AddInvitation saveInvitation(AddInvitation addInvitation){
        return addInvitationRepository.save(addInvitation);
    }
    public void deleteById(Long id){
        addInvitationRepository.deleteById(id);
    }
    public AddInvitation updateRecordById(Long id, AddInvitation record) {

        Optional<AddInvitation> invitationRecord= addInvitationRepository.findById(id);
        if(invitationRecord.isPresent()){
            AddInvitation invitation =  invitationRecord.get();
            addInvitationRepository.save(invitation);

        }
        return addInvitationRepository.save(record);
    }

}
