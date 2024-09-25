package com.masters.service;

import com.masters.entity.StartWeek;
import com.masters.repository.StartWeekRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StartWeekService {
    @Autowired
    private StartWeekRepository startWeekRepository;

    @Autowired
    private ModelMapper modelMapper;

     public List<StartWeek> getAll(){
         return startWeekRepository.findAll();
     }

     public StartWeek getById(Long id){
         return startWeekRepository.findById(id).orElseThrow(()->new RuntimeException("data not found"));
     }
     public StartWeek create(StartWeek startWeek){
         return startWeekRepository.save(startWeek);
     }

     public void deleteById(Long id){
         startWeekRepository.deleteById(id);
     }

    public StartWeek updateRecordById(Long id, StartWeek record) {

        Optional<StartWeek> startWeekRecord = startWeekRepository.findById(id);
        if(startWeekRecord.isPresent()){
            StartWeek startWeek =  startWeekRecord.get();
            modelMapper.getConfiguration().setSkipNullEnabled(true);
            modelMapper.map(record,startWeek);
            startWeekRepository.save(startWeek);

        }
        return startWeekRepository.save(record);
    }



}
