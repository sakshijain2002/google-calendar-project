package com.masters.service;


import com.masters.entity.Visibility;
import com.masters.repository.VisibilityRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VisibilityService {

    @Autowired
    private VisibilityRepository visibilityRepository;

    @Autowired
    private ModelMapper modelMapper;

    public List<Visibility> getAll(){
        return visibilityRepository.findAll();
    }
    public Visibility getById(Long id){
        return visibilityRepository.findById(id).orElseThrow(()->new RuntimeException("data not found"));
    }

    public Visibility create(Visibility visibility){
        return visibilityRepository.save(visibility);
    }
    public void deleteById(Long id){
        visibilityRepository.deleteById(id);
    }

    public Visibility updateRecordById(Long id,Visibility record){
        Optional<Visibility> visibilityRecord = visibilityRepository.findById(id);
        if(visibilityRecord.isPresent()){
            Visibility visibility =  visibilityRecord.get();
            modelMapper.getConfiguration().setSkipNullEnabled(true);
            modelMapper.map(record,visibility);
            visibilityRepository.save(visibility);

        }
        return record;
    }

}


