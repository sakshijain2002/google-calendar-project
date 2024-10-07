package com.masters.service;

import com.masters.entity.Translation;
import com.masters.repository.TranslationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class TranslationService {

    @Autowired
    private TranslationRepository translationRepository;

    public List<Translation> getAll(){
        return translationRepository.findAll();
    }

    public Translation getById(Integer id){
        return translationRepository.findById(id).orElseThrow(()->new RuntimeException("data not found"));
    }

    public void saveTranslations(Map<String, String> translations) {
        translations.forEach((key, value) -> {
            Translation translation = new Translation();
            translation.setKeyName(key);
            translation.setKeyValue(value);
            translationRepository.save(translation);
        });
    }
    public void deleteById(Integer id){
        translationRepository.deleteById(id);
    }

}
