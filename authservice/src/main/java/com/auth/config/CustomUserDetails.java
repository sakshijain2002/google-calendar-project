package com.auth.config;


import com.auth.entity.Role;
import com.auth.entity.UserCredential;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class CustomUserDetails implements UserDetails {
    private String email;
    private String password;
    private List<GrantedAuthority> roles;
    private UserCredential userCredential;
    public CustomUserDetails(UserCredential userCredential) {
        this.email = userCredential.getEmail();
        this.password = userCredential.getPassword();
        roles = (userCredential.getRole().stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getRole())).collect(Collectors.toList()));


    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }



    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
