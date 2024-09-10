package com.masters.service;


import com.masters.entity.StartTime;
import com.masters.repository.StartTimeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StartTimeService {

    @Autowired
    private StartTimeRepository startTimeRepository;

    @Autowired
    private ModelMapper modelMapper;

    public List<StartTime> getAll(){
        return startTimeRepository.findAll();
    }
    public StartTime getById(Long id){
        return startTimeRepository.findById(id).orElseThrow(()-> new RuntimeException("data not found"));
    }
    public StartTime save(StartTime startTime){
        return startTimeRepository.save(startTime);
    }
    public void deleteById(Long id){
        startTimeRepository.deleteById(id);
    }
    public StartTime updateRecordById(Long id,StartTime record) {
        Optional<StartTime> startRecord = startTimeRepository.findById(id);
        if (startRecord.isPresent()) {
            StartTime startTime = startRecord.get();
            modelMapper.getConfiguration().setSkipNullEnabled(true);
            modelMapper.map(record, startTime);
            startTimeRepository.save(startTime);

        }
        return record;
    }
}
