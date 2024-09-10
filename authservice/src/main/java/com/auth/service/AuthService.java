package com.auth.service;


import com.auth.entity.Role;
import com.auth.entity.UserCredential;

import com.auth.repository.RoleRepository;
import com.auth.repository.UserCredentialRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

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

//    public String saveUser(UserCredential credential) {
//
//        credential.setPassword(passwordEncoder.encode(credential.getPassword()));
//        repository.save(credential);
//        return "user added to the system";
//    }
 public UserCredential saveUser(UserCredential userCredential) {

    // Encrypt the password
    userCredential.setPassword(passwordEncoder.encode(userCredential.getPassword()));
    if (repository.existsByEmail(userCredential.getEmail())) {
        throw new RuntimeException("Email address already exists.");
    }

    // Assign roles to the user
    Set<Role> roles = userCredential.getRole();
    if (roles != null) {
        for (Role role : roles) {
            // Ensure roles exist in the database
            Role existingRole = roleRepository.findByRole(role.getRole());
            if (existingRole != null) {
                role.setId(existingRole.getId());
            } else {
                // Handle the case where the role does not exist
                // You might want to create and save the role if necessary
            }
        }
    }


    // Save the user with roles
    return repository.save(userCredential);

}

 public List<UserCredential> getAll(){
    return  repository.findAll();
 }
 public UserCredential getById(Integer id){
    return repository.findById(id).orElseThrow(()->new RuntimeException("data not found"));
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
                .orElseThrow(() -> new RuntimeException("User profile not found with email" +email));
    }

    public void deleteUserById(Integer id){
        repository.deleteById(id);
    }

//    public UserCredential updateRecordById(Integer id, UserCredential record) {
//
//        Optional<UserCredential> userRecord = repository.findById(id);
//        if (userRecord.isPresent()) {
//            UserCredential user = userRecord.get();
//            modelMapper.getConfiguration().setSkipNullEnabled(true);
//            modelMapper.map(record,user);
//             repository.save(user);
//        }
//       return record;
//    }
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


}