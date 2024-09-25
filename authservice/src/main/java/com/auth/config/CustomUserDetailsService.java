package com.auth.config;


import com.auth.entity.Role;
import com.auth.entity.UserCredential;

import com.auth.repository.UserCredentialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Component
@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private  UserCredentialRepository repository;




    //    @Override
//    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
//        Optional<UserCredential> credential = repository.findByEmail(email); // Find by name, not email
//        return credential.map(CustomUserDetails::new)
//                .orElseThrow(() -> new UsernameNotFoundException("user not found with email: " + email));
//    }
@Override
public UserDetails loadUserByUsername(String email) {
    UserCredential user = repository.findByEmail(email)
            .orElseThrow(() -> new UsernameNotFoundException("User not found"));

    // Convert roles to GrantedAuthority
    List<GrantedAuthority> authorities = user.getRole().stream()
            .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getRole()))
            .collect(Collectors.toList());

    // Return CustomUserDetails with authorities
    return new CustomUserDetails(user);
}
}

//
