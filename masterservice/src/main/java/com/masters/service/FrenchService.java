package com.masters.service;

import com.masters.entity.French;
import com.masters.entity.Translation;
import com.masters.repository.FrenchRepository;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class FrenchService {
    @Autowired
    private FrenchRepository frenchRepository;

    public List<French> getAll(){
        return frenchRepository.findAll();
    }

    public French getById(Integer id){
        return frenchRepository.findById(id).orElseThrow(()->new RuntimeException("data not found"));
    }

    public void saveTranslations(Map<String, String> translations) {
        translations.forEach((key, value) -> {
           French french = new French();
            french.setKeyName(key);
            french.setKeyValue(value);
            frenchRepository.save(french);
        });
    }

    public void deleteById(Integer id){
        frenchRepository.deleteById(id);
    }
}
