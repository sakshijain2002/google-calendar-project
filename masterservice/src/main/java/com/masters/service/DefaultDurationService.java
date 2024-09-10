package com.masters.service;

import com.masters.entity.DefaultDuration;
import com.masters.repository.DefaultDurationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DefaultDurationService {
    @Autowired
    private DefaultDurationRepository defaultDurationRepository;

    public List<DefaultDuration> getAll(){
        return defaultDurationRepository.findAll();
    }

    public DefaultDuration getById(Long id){
       return defaultDurationRepository.findById(id).orElseThrow(()->new RuntimeException("data does not exist"));
    }
    public DefaultDuration createDuration(DefaultDuration defaultDuration){
        return defaultDurationRepository.save(defaultDuration);
    }

    public void deleteById(Long id){
        defaultDurationRepository.deleteById(id);
    }
    public DefaultDuration updateRecordById(Long id, DefaultDuration record) {

        Optional<DefaultDuration> defaultDurationRecord = defaultDurationRepository.findById(id);
        if(defaultDurationRecord.isPresent()){
           DefaultDuration defaultDuration =  defaultDurationRecord.get();
            defaultDurationRepository.save(defaultDuration);

        }
        return defaultDurationRepository.save(record);
    }


}
