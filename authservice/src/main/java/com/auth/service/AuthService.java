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
        // Encrypt the password
        userCredential.setPassword(passwordEncoder.encode(userCredential.getPassword()));

        // Check if the email already exists
        if (repository.existsByEmail(userCredential.getEmail())) {
            throw new RuntimeException("Email address already exists.");
        }

        // Save the user with roles
        UserCredential savedUser = repository.save(userCredential);

        // Prepare the response
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
        // Parse the JWT token to extract claims (like username or userId)
        Claims claims = Jwts.parser()
                .setSigningKey(secretKey) // Use your secret key to validate the token
                .parseClaimsJws(accessToken)
                .getBody();

        String email = claims.getSubject(); // Assuming the 'sub' field contains the username

        // Fetch user credentials from the repository by username (or another identifier)
        return repository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User profile not found with email" + email));
    }


    public void deleteUserById(Integer id) {

        repository.deleteById(id);
    }

    public void deleteUserByEmail(String email) {
        UserCredential user = repository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Clear the user-role associations
        user.getRole().clear();
        repository.save(user);

        // Now delete the user
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

        // Extract email (subject) from the JWT token
        Claims claims = Jwts.parser()
                .setSigningKey(secretKey) // Use your secret key to validate the token
                .parseClaimsJws(accessToken)
                .getBody();

        String email = claims.getSubject();

        // Find the user by email
        Optional<UserCredential> userRecord = repository.findByEmail(email);
        if (userRecord.isPresent()) {
            UserCredential user = userRecord.get();

            // Use ModelMapper to map non-null fields from the incoming record to the existing user
            modelMapper.getConfiguration().setSkipNullEnabled(true);
            modelMapper.map(record, user);

            // Save the updated user back to the repository
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

//    public String getRolesByEmail(String email) {
//        Optional<UserCredential> user = repository.findByEmail(email);
//        if (user.isPresent()) {
//            // Get the roles and join the role names as a comma-separated string
//            Set<Role> roles = user.get().getRole();
//            return roles.stream()
//                    .map(Role::getRole)  // Convert Role object to role name string
//                    .collect(Collectors.joining(", "));  // Join role names into a single string
//        }
//        return "No roles found"; // Or throw an exception if the user is not found
//    }
    public UserActivityDto getUserActivity(String email) {
        List<Task> tasks = taskClient.getTaskByEmail(email);
        List<Event> events = eventClient.getByEmailId(email);
        UserCredential credentials = repository.findByEmail(email).orElseThrow(()->new RuntimeException("data not found"));

        return new UserActivityDto(credentials,events,tasks);
    }

    public void updateUserRole(UpdateRoleRequest updateRoleRequest) {
        String email = updateRoleRequest.getEmail();
        String newRole = updateRoleRequest.getNewRole();

        // Find the user by email
        Optional<UserCredential> userOptional = repository.findByEmail(email);

        if (userOptional.isPresent()) {
            // Get the user
            UserCredential user = userOptional.get();

            // Update the user's role
            user.updateRole(newRole, roleRepository);

            // Save the updated user
            repository.save(user);
        } else {
            throw new RuntimeException("User with email " + email + " not found");
        }
    }


}