package com.masters.service;

import com.masters.entity.Country;
import com.masters.repository.CountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CountryService {
    @Autowired
    private CountryRepository countryRepository;

    public Country getById(Long id){
        return countryRepository.findById(id).orElseThrow(()-> new RuntimeException());
    }

    public List<Country> getAll(){
        return countryRepository.findAll();
    }

    public Country createCountry(Country country){
        return countryRepository.save(country);
    }

    public void deleteById(Long id){
        countryRepository.deleteById(id);
    }

    public Country updateRecordById(Long id, Country record) {

        Optional<Country> countryRecord = countryRepository.findById(id);
        if(countryRecord.isPresent()){
            Country country =  countryRecord.get();
            countryRepository.save(country);

        }
        return countryRepository.save(record);
    }

}
