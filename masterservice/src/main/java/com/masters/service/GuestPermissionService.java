package com.masters.service;

import com.masters.entity.GuestPermission;
import com.masters.repository.GuestPermissionRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GuestPermissionService {
    @Autowired
    private GuestPermissionRepository guestPermissionRepository;

    @Autowired
    private ModelMapper modelMapper;

    public List<GuestPermission> getAll(){
        return guestPermissionRepository.findAll();
    }

    public GuestPermission getById(Long id){
        return guestPermissionRepository.findById(id).orElseThrow(()-> new RuntimeException("data not found"));
    }
    public GuestPermission saveGuestPermission(GuestPermission guestPermission){
        return guestPermissionRepository.save(guestPermission);
    }
    public void deleteById(Long id){
        guestPermissionRepository.deleteById(id);
    }

    public GuestPermission updateRecordById(Long id, GuestPermission record) {

        Optional<GuestPermission> permissionRecord = guestPermissionRepository.findById(id);
        if(permissionRecord.isPresent()){
            GuestPermission guestPermission =  permissionRecord.get();
            modelMapper.getConfiguration().setSkipNullEnabled(true);
            modelMapper.map(record,guestPermission);
            guestPermissionRepository.save(guestPermission);

        }
        return guestPermissionRepository.save(record);
    }

}
