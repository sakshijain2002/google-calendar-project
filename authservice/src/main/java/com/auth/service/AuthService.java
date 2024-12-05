package com.auth.service;


import com.auth.entity.Role;
import com.auth.entity.UserCredential;
import com.auth.model.Event;
import com.auth.model.Task;
import com.auth.model.UpdateRoleRequest;
import com.auth.model.UserActivityDto;
import com.auth.repository.RoleRepository;
import com.auth.repository.UserCredentialRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class AuthService {

    @Autowired
    private UserCredentialRepository repository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private JwtService jwtService;
    @Autowired
    private ModelMapper modelMapper;

    @Value("${jwt.secretKey}")
    private String secretKey;

    @Autowired
    private TaskClient taskClient;

    @Autowired
    private EventClient eventClient;
    public Map<String, Object> saveUser(UserCredential userCredential) {
        userCredential.setPassword(passwordEncoder.encode(userCredential.getPassword()));
        if (repository.existsByEmail(userCredential.getEmail())) {
            throw new RuntimeException("Email address already exists.");
        }
        UserCredential savedUser = repository.save(userCredential);
        Map<String, Object> response = new HashMap<>();
        response.put("userId", savedUser.getId());
        response.put("message", "User created successfully");

        return response;
    }

    public List<UserCredential> getAll() {
        return repository.findAll();
    }

    public UserCredential getById(Integer id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("data not found"));
    }

    public UserCredential getByEmail(String email) {
        return repository.findByEmail(email).orElseThrow(() -> new RuntimeException("data not found"));
    }

    public UserCredential getProfileFromJwt(String accessToken) {
        Claims claims = Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(accessToken)
                .getBody();

        String email = claims.getSubject();
        return repository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User profile not found with email" + email));
    }


    public void deleteUserById(Integer id) {

        repository.deleteById(id);
    }

    public void deleteUserByEmail(String email) {
        UserCredential user = repository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.getRole().clear();
        repository.save(user);
        repository.delete(user);
    }

    public UserCredential updateRecordById(Integer id, UserCredential record) {

        Optional<UserCredential> userRecord = repository.findById(id);
        if (userRecord.isPresent()) {
            UserCredential user = userRecord.get();
            modelMapper.getConfiguration().setSkipNullEnabled(true);
            modelMapper.map(record, user);
            repository.save(user);
        }
        return record;
    }

    public UserCredential updateUserProfileByToken(UserCredential record, String accessToken) {


        Claims claims = Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(accessToken)
                .getBody();

        String email = claims.getSubject();


        Optional<UserCredential> userRecord = repository.findByEmail(email);
        if (userRecord.isPresent()) {
            UserCredential user = userRecord.get();


            modelMapper.getConfiguration().setSkipNullEnabled(true);
            modelMapper.map(record, user);


            repository.save(user);

            return user;
        } else {
            throw new EntityNotFoundException("User not found for email: " + email);
        }
    }


    public String generateToken(String username) {
        return jwtService.generateToken(username);
    }

    public String extractEmail(String token) {
        return jwtService.extractEmail(token);
    }


    public UserActivityDto getUserActivity(String email) {
        UserCredential credentials = repository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("data not found"));


        List<Task> tasks = Collections.emptyList();
        List<Event> events = Collections.emptyList();


        if (credentials.getAccountStatus().equalsIgnoreCase("public")) {
            tasks = taskClient.getTaskByEmail(email);
            events = eventClient.getByEmailId(email);
        }


        return new UserActivityDto(credentials, events, tasks);
    }

    public void updateUserRole(UpdateRoleRequest updateRoleRequest) {
        String email = updateRoleRequest.getEmail();
        String role = updateRoleRequest.getRole();


        Optional<UserCredential> userOptional = repository.findByEmail(email);

        if (userOptional.isPresent()) {

            UserCredential user = userOptional.get();
            user.updateRole(role, roleRepository);
            repository.save(user);
        } else {
            throw new RuntimeException("User with email " + email + " not found");
        }
    }


}