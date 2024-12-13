package com.auth.controller;

import com.auth.entity.RefreshToken;
import com.auth.entity.UserCredential;
import com.auth.model.*;
import com.auth.service.AuthService;
import com.auth.service.RefreshTokenService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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

    @GetMapping("/admin/users")
    public List<UserCredential> getAll() {
        return service.getAll();
    }

    @GetMapping("/user/get/{id}")
    public UserCredential getById(@PathVariable Integer id) {
        return service.getById(id);
    }

    @GetMapping("/user/getEmail/{email}")
    public UserCredential getByEmail(@PathVariable String email) {
        return service.getByEmail(email);
    }

    @GetMapping("/get/user")
    public UserCredential getProfile(@RequestHeader("Authorization") String authorizationHeader) {
        String token = authorizationHeader.replace("Bearer ", "");
        return service.getProfileFromJwt(token);
    }

    @PostMapping("/register")
    public ResponseEntity<Map<String, Object>> createUser(@RequestBody UserCredential userCredential) {
        try {
            Map<String, Object> response = service.saveUser(userCredential);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(Map.of("message", e.getMessage()), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(Map.of("message", "An unexpected error occurred"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/token")
    public JwtResponse getToken(@RequestBody AuthRequest authRequest) {
        try {
            Authentication authenticate = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword())
            );

            if (authenticate.isAuthenticated()) {
                RefreshToken existingToken = refreshTokenService.findByEmail(authRequest.getEmail())
                        .orElseGet(() -> refreshTokenService.createRefreshToken(authRequest.getEmail()));
                return JwtResponse.builder()
                        .accessToken(service.generateToken(authRequest.getEmail()))
                        .refreshToken(existingToken.getRefreshToken())
                        .build();
            } else {
                throw new RuntimeException("Invalid credentials or access denied");
            }
        } catch (Exception e) {

            System.err.println("Error during token generation: " + e.getMessage());
            throw new RuntimeException("Error during token generation");
        }
    }

    @PostMapping("/refreshToken")
    public JwtResponse refreshToken(@RequestBody RefreshTokenRequest refreshTokenRequest) {
        return refreshTokenService.findByToken(refreshTokenRequest.getRefreshToken())
                .map(refreshTokenService::verifyExpiration)
                .map(RefreshToken::getUserCredential)
                .map(userCredential -> {
                    String accessToken = service.generateToken(userCredential.getEmail());
                    return JwtResponse.builder()
                            .accessToken(accessToken)
                            .refreshToken(refreshTokenRequest.getRefreshToken())
                            .build();

                }).orElseThrow(() -> new RuntimeException("Refresh Token is not in database"));

    }


    @PutMapping("/update/user")
    public ResponseEntity<UserCredential> updateUserProfile(
            @RequestBody UserCredential userProfile,
            HttpServletRequest request) {

        String accessToken = request.getHeader("Authorization").substring(7);

        try {
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

    @GetMapping("/email")
    public String getEmail(@RequestHeader("Authorization") String authorizationHeader) {
        String token = authorizationHeader.replace("Bearer ", "");
        return service.extractEmail(token);
    }

    @Secured("ROLE_ADMIN")
    @DeleteMapping("/delete/{userId}")
    public void deleteById(@PathVariable Integer userId) {
        service.deleteUserById(userId);
    }

    @DeleteMapping("/admin/delete/{email}")
    public void deleteByEmail(@PathVariable String email){
        service.deleteUserByEmail(email);
    }

    @GetMapping("/search/{email}")
    public UserActivityDto getUserActivity(@PathVariable String email) {
        return service.getUserActivity(email);
    }
    @PutMapping("/admin/change-role")
    public String updateUserRole(@RequestBody UpdateRoleRequest updateRoleRequest) {
        try {
            service.updateUserRole(updateRoleRequest);
            return "User role updated successfully!";
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }
}
