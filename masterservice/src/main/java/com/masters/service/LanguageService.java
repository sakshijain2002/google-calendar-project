package com.masters.service;

import com.masters.entity.Language;
import com.masters.repository.LanguageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LanguageService {
    @Autowired
    private LanguageRepository languageRepository;

    public List<Language> getAll(){
        return languageRepository.findAll();
    }

    public Language getById(Long id){
        return languageRepository.findById(id).orElseThrow(()-> new RuntimeException("data not found"));
    }
    public Language createLanguage(Language language){
        return languageRepository.save(language);
    }

    public void deleteById(Long id){
        languageRepository.deleteById(id);
    }
    public Language updateRecordById(Long id, Language record) {

        Optional<Language> languageRecord = languageRepository.findById(id);
        if(languageRecord.isPresent()){
            Language language =  languageRecord.get();
            languageRepository.save(language);

        }
        return languageRepository.save(record);
    }

}
