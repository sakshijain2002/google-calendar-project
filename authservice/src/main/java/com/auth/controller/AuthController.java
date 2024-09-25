package com.auth.controller;

import com.auth.entity.RefreshToken;
import com.auth.entity.Role;
import com.auth.entity.UserCredential;
import com.auth.model.AuthRequest;
import com.auth.model.JwtResponse;
import com.auth.model.RefreshTokenRequest;
import com.auth.service.AuthService;
import com.auth.service.RefreshTokenService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/auth")
@CrossOrigin
public class AuthController {
    @Autowired
    private AuthService service;

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private RefreshTokenService refreshTokenService;

    private UserCredential user;

    @GetMapping("/getAll")
    public List<UserCredential> getAll(){
        return service.getAll();
    }
    @GetMapping("/user/get/{id}")
    public UserCredential getById(@PathVariable Integer id){
        return service.getById(id);
    }
    @GetMapping("/user/getEmail/{email}")
    public UserCredential getByEmail(@PathVariable String email){
        return service.getByEmail(email);
    }
    @GetMapping("/get/user")
    public UserCredential getProfile(@RequestHeader("Authorization") String authorizationHeader) {
        // Extract the token from the Authorization header (assumes "Bearer <token>" format)
        String token = authorizationHeader.replace("Bearer ", "");

        // Pass the extracted token to your service
        return service.getProfileFromJwt(token);
    }

    @PostMapping("/register")
    public ResponseEntity<Map<String, Object>> createUser(@RequestBody UserCredential userCredential) {
        try {
            // Call the service method to save the user and get the response
            Map<String, Object> response = service.saveUser(userCredential);

            // Return the response with HTTP status 201 (Created)
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            // Handle known exceptions (e.g., email already exists)
            return new ResponseEntity<>(Map.of("message", e.getMessage()), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            // Handle other unexpected exceptions
            return new ResponseEntity<>(Map.of("message", "An unexpected error occurred"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


   @PostMapping("/token")
    public JwtResponse getToken(@RequestBody AuthRequest authRequest) {
    try {
        // Authenticate the user
        Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword())
        );

        if (authenticate.isAuthenticated()) {

            // Check if a refresh token already exists for this user
            RefreshToken existingToken = refreshTokenService.findByEmail(authRequest.getEmail())
                    .orElseGet(() -> refreshTokenService.createRefreshToken(authRequest.getEmail()));

            // Generate new access token
//            String accessToken = service.generateToken(authRequest.getUsername());

            // Return response with new access token and existing or new refresh token
            return JwtResponse.builder()
                    .accessToken(service.generateToken(authRequest.getEmail()))
                    .refreshToken(existingToken.getRefreshToken())
                    .build();
        } else {
            throw new RuntimeException("Invalid credentials or access denied");
        }
    } catch (Exception e) {
        // Log the exception and provide a meaningful error response
        System.err.println("Error during token generation: " + e.getMessage());
        throw new RuntimeException("Error during token generation");
    }
}

    @PostMapping("/refreshToken")
    public JwtResponse refreshToken(@RequestBody RefreshTokenRequest refreshTokenRequest){
     return refreshTokenService.findByToken(refreshTokenRequest.getRefreshToken())
             .map(refreshTokenService::verifyExpiration)
             .map(RefreshToken::getUserCredential)
             .map(userCredential -> {
                 String accessToken = service.generateToken(userCredential.getEmail());
                 return JwtResponse.builder()
                         .accessToken(accessToken)
                         .refreshToken(refreshTokenRequest.getRefreshToken())
                         .build();

             }).orElseThrow(()->new RuntimeException("Refresh Token is not in database"));

    }

//    @PutMapping("/update/{id}")
//    public UserCredential updateRecordById(@PathVariable Integer id,@RequestBody UserCredential userCredential){
//        return service.updateRecordById(id,userCredential);
//    }

    @PutMapping("/update/user")
    public ResponseEntity<UserCredential> updateUserProfile(
            @RequestBody UserCredential userProfile,
            HttpServletRequest request) {

        // Extract JWT token from the Authorization header
        String accessToken = request.getHeader("Authorization").substring(7); // Remove 'Bearer ' from the token

        try {
            // Update the user profile using the JWT token
            UserCredential updatedUser = service.updateUserProfileByToken(userProfile, accessToken);
            return ResponseEntity.ok(updatedUser);

        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/admin/dashboard")
    public String getAdminDashboard() {
        return "Admin Dashboard";
    }

    @GetMapping("/getRole/{email}")
    public String getRoleById(@PathVariable String email){
        return service.getRolesByEmail(email);
    }


    @GetMapping("/email")
    public String getEmail(@RequestHeader("Authorization") String authorizationHeader){
        String token = authorizationHeader.replace("Bearer ", "");
        return service.extractEmail(token);
    }

    @Secured("ROLE_ADMIN")
    @DeleteMapping("/delete/{userId}")
    public void deleteById(@PathVariable Integer userId){
        service.deleteUserById(userId);
    }
}
