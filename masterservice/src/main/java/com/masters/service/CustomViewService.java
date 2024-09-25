package com.masters.service;

import com.masters.entity.CustomView;
import com.masters.repository.CustomViewRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomViewService {

    @Autowired
    private CustomViewRepository customViewRepository;

    @Autowired
    private ModelMapper modelMapper;

    public List<CustomView> getAll(){
        return customViewRepository.findAll();
    }

    public CustomView getById(Long id){
        return customViewRepository.findById(id).orElseThrow(()-> new RuntimeException());
    }
    public CustomView saveCustomView(CustomView view){
        return customViewRepository.save(view);
    }

    public void deleteById(Long id){
        customViewRepository.deleteById(id);
    }

    public CustomView updateRecordById(Long id, CustomView record) {

        Optional<CustomView> viewRecord = customViewRepository.findById(id);
        if(viewRecord.isPresent()){
            CustomView view =  viewRecord.get();
            modelMapper.getConfiguration().setSkipNullEnabled(true);
            modelMapper.map(record,view);
           customViewRepository.save(view);

        }
        return customViewRepository.save(record);
    }
}
