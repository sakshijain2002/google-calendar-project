package com.masters.service;

import com.masters.entity.Gender;

import com.masters.repository.GenderRepository;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GenderService {
    @Autowired
    private GenderRepository genderRepository;
    @Autowired
    private ModelMapper modelMapper;

    public List<Gender> getAll(){
      return   genderRepository.findAll();
    }
    public Gender getById(Integer id){
        return genderRepository.findById(id).orElseThrow(()->new RuntimeException("data not found"));
    }
    public Gender save(Gender gender){
        return genderRepository.save(gender);
    }
    public void deleteById(Integer id){
        genderRepository.deleteById(id);
    }
    public Gender updateRecordById(Integer id,Gender record){
        Optional<Gender> genderRecord = genderRepository.findById(id);
        if(genderRecord.isPresent()){
            Gender gender =  genderRecord.get();
            modelMapper.getConfiguration().setSkipNullEnabled(true);
            modelMapper.map(record,gender);
            genderRepository.save(gender);

        }
        return record;
    }
    }

