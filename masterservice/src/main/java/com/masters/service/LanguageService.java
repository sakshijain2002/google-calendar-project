package com.masters.service;

import com.masters.entity.Language;
import com.masters.model.Role;
import com.masters.model.UserModel;
import com.masters.repository.LanguageRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class LanguageService {
    @Autowired
    private LanguageRepository languageRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserServiceClient userServiceClient;

    public List<Language> getAll(){
        return languageRepository.findAll();
    }


    public Language getById(Long id){
        return languageRepository.findById(id).orElseThrow(()-> new RuntimeException("data not found"));
    }
    public Language createLanguage(Language language){
        return languageRepository.save(language);
    }


//    public void deleteById(Long id, String email) {
//        // Fetch the user's roles as a string (e.g., "ADMIN, USER")
//        String userRole = userServiceClient.getRoleByEmail(email);
//
//        // Check if the user has the "ADMIN" role (case-insensitive)
//        if (userRole == null || !Arrays.stream(userRole.split(","))
//                .anyMatch(role -> role.trim().equalsIgnoreCase("admin"))) {
//            throw new RuntimeException("Access denied");
//        }
//
//        // Now, check if the language entry exists before deleting
//        if (!languageRepository.existsById(id)) {
//            throw new EntityNotFoundException("Language entry not found with id: " + id);
//        }
//
//        // Proceed with deletion if the user has the ADMIN role
//        languageRepository.deleteById(id);
//    }
        // Fetch user information from another microservice using userServiceClient
    public void deleteById(Long id){
        languageRepository.deleteById(id);
    }


    public Language updateRecordById(Long id, Language record) {

        Optional<Language> languageRecord = languageRepository.findById(id);
        if(languageRecord.isPresent()){
            Language language =  languageRecord.get();
            modelMapper.getConfiguration().setSkipNullEnabled(true);
            modelMapper.map(record,language);
            languageRepository.save(language);

        }
        return languageRepository.save(record);
    }

}
