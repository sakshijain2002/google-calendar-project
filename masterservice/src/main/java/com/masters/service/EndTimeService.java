package com.masters.service;


import com.masters.entity.EndTime;
import com.masters.repository.EndTimeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EndTimeService {
    @Autowired
    private EndTimeRepository endTimeRepository;


    @Autowired
    private ModelMapper modelMapper;

    public List<EndTime> getAll(){
        return endTimeRepository.findAll();
    }
    public EndTime getById(Long id){
        return endTimeRepository.findById(id).orElseThrow(()->new RuntimeException("data not found"));
    }

    public EndTime save(EndTime endTime){
        return endTimeRepository.save(endTime);
    }
    public EndTime updateRecordById(Long id,EndTime record) {
        Optional<EndTime> endRecord = endTimeRepository.findById(id);
        if (endRecord.isPresent()) {
            EndTime endTime = endRecord.get();
            modelMapper.getConfiguration().setSkipNullEnabled(true);
            modelMapper.map(record, endTime);
            endTimeRepository.save(endTime);

        }
        return record;
    }
    public void deleteById(Long id){
        endTimeRepository.deleteById(id);
    }
}
