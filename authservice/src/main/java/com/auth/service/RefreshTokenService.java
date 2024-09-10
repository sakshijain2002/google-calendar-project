package com.auth.service;

import com.auth.entity.RefreshToken;
import com.auth.entity.UserCredential;
import com.auth.repository.RefreshTokenRepository;
import com.auth.repository.UserCredentialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
public class RefreshTokenService {
    @Autowired
    private RefreshTokenRepository tokenRepository;
    @Autowired
    private UserCredentialRepository userRepository;

    public RefreshToken createRefreshToken(String email){
        RefreshToken refreshToken = RefreshToken.builder()
                .userCredential(userRepository.findByEmail(email).get())
                .refreshToken(UUID.randomUUID().toString())
                .expiryDate(Instant.now().plusMillis(604800000))
                .build();
        return tokenRepository.save(refreshToken);
    }

    public Optional<RefreshToken> findByToken(String refreshToken){
        return tokenRepository.findByRefreshToken(refreshToken);
    }
    public RefreshToken verifyExpiration(RefreshToken refreshToken){
        if(refreshToken.getExpiryDate().compareTo(Instant.now())<0){
            tokenRepository.delete(refreshToken);
            throw new RuntimeException(refreshToken.getRefreshToken() +"Refresh Token was expired");
        }
        return refreshToken;
    }
    public Optional<RefreshToken> findByEmail(String email) {
        UserCredential user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return tokenRepository.findByUserCredential(user);
    }
}