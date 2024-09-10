package com.masters.service;

import com.masters.entity.EmailEventPrivacy;
import com.masters.repository.EmailEventPrivacyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmailEventPrivacyService {
    @Autowired
    private EmailEventPrivacyRepository emailEventPrivacyRepository;

    public List<EmailEventPrivacy> getAll(){
        return emailEventPrivacyRepository.findAll();
    }

    public EmailEventPrivacy getById( Long id){
        return emailEventPrivacyRepository.findById(id).orElseThrow(()-> new RuntimeException("data not found"));
    }

    public EmailEventPrivacy save(EmailEventPrivacy emailEventPrivacy){
        return emailEventPrivacyRepository.save(emailEventPrivacy);
    }
    public void deleteById( Long id){
        emailEventPrivacyRepository.deleteById(id);
    }

    public EmailEventPrivacy updateRecordById(Long id, EmailEventPrivacy record) {

        Optional<EmailEventPrivacy> emailEventPrivacyRecord = emailEventPrivacyRepository.findById(id);
        if(emailEventPrivacyRecord.isPresent()){
         EmailEventPrivacy emailEventPrivacy = emailEventPrivacyRecord.get();
            emailEventPrivacyRepository.save(emailEventPrivacy);

        }
        return emailEventPrivacyRepository.save(record);
    }

}
