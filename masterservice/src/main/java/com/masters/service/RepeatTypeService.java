package com.masters.service;


import com.masters.entity.RepeatType;
import com.masters.repository.RepeatTypeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RepeatTypeService {
    @Autowired
    private RepeatTypeRepository repeatTypeRepository;

    @Autowired
    private ModelMapper modelMapper;
    public List<RepeatType> getAll(){
        return repeatTypeRepository.findAll();
    }

    public RepeatType getById(Long id){
        return repeatTypeRepository.findById(id).orElseThrow(()->new RuntimeException("data not found"));
    }
    public RepeatType save(RepeatType type){
        return repeatTypeRepository.save(type);
    }

    public void deleteById(Long id){
        repeatTypeRepository.deleteById(id);
    }
    public RepeatType updateRecordById(Long id,RepeatType record){
        Optional<RepeatType> typeRecord = repeatTypeRepository.findById(id);
        if(typeRecord.isPresent()){
            RepeatType type =  typeRecord.get();
            modelMapper.getConfiguration().setSkipNullEnabled(true);
            modelMapper.map(record,type);
            repeatTypeRepository.save(type);

        }
        return record;

    }


}
