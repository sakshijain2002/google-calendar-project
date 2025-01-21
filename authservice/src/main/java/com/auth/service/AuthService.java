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
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
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
//    public Map<String, Object> saveUser(UserCredential userCredential) {
//        userCredential.setPassword(passwordEncoder.encode(userCredential.getPassword()));
//        if (repository.existsByEmail(userCredential.getEmail())) {
//            throw new RuntimeException("Email address already exists.");
//        }
//        UserCredential savedUser = repository.save(userCredential);
//        Map<String, Object> response = new HashMap<>();
//        response.put("userId", savedUser.getId());
//        response.put("message", "User created successfully");
//
//        return response;
//    }

    @Transactional
    public Map<String, Object> saveUser(UserCredential userCredential) {
        // Encode the password
        userCredential.setPassword(passwordEncoder.encode(userCredential.getPassword()));

        // Check if the email already exists
        if (repository.existsByEmail(userCredential.getEmail())) {
            throw new RuntimeException("Email address already exists.");
        }

        // Ensure roles are unique and properly persisted
        Set<Role> validRoles = new HashSet<>();
        for (Role role : userCredential.getRole()) {
            Role existingRole = roleRepository.findByRole(role.getRole())
                    .orElseGet(() -> roleRepository.save(new Role(role.getRole()))); // Save if not found
            validRoles.add(existingRole);
        }
        userCredential.setRole(validRoles);

        // Save the user
        UserCredential savedUser = repository.save(userCredential);

        // Create the response
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

    @Transactional
    public UserCredential updateUserProfileByToken(UserCredential record, String accessToken) {
        Claims claims;
        try {
            claims = Jwts.parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(accessToken)
                    .getBody();
        } catch (JwtException e) {
            throw new SecurityException("Invalid or expired token.", e);
        }

        String email = claims.getSubject();
        Optional<UserCredential> userRecord = repository.findByEmail(email);

        if (userRecord.isEmpty()) {
            throw new EntityNotFoundException("User not found for email: " + email);
        }

        UserCredential user = userRecord.get();

        // Update roles if provided
        if (record.getRole() != null && !record.getRole().isEmpty()) {
            Set<Role> updatedRoles = new HashSet<>();
            for (Role role : record.getRole()) {
                Role existingRole = roleRepository.findByRole(role.getRole())
                        .orElseThrow(() -> new RuntimeException("Role '" + role.getRole() + "' not found in the database."));
                updatedRoles.add(existingRole);
            }
            user.setRole(updatedRoles);
        }

        // Map other fields
        modelMapper.getConfiguration().setSkipNullEnabled(true);
        modelMapper.map(record, user);

        // Save updated user
        return repository.save(user);
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