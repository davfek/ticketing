package com.example.ticketing.service;

import com.example.ticketing.config.TokenConfig;
import com.example.ticketing.repository.UserRepository;
import com.example.ticketing.user.AuthenticationResult;
import com.example.ticketing.user.LoginRequest;
import com.example.ticketing.user.LoginResponse;
import com.example.ticketing.user.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;

@Service
public class LoginService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final TokenConfig tokenConfig;


    @Autowired
    public LoginService(UserRepository userRepository, TokenConfig tokenConfig){
        this.userRepository=userRepository;
        this.passwordEncoder=new BCryptPasswordEncoder();
        this.tokenConfig=tokenConfig;
    }



    public ResponseEntity<LoginResponse> login(LoginRequest loginRequest){
        String username = loginRequest.getUsername();
        String password = loginRequest.getPassword();

        AuthenticationResult result = authenticateUser(username, password);

        if (result.isAuthenticated()) {
            LoginResponse response = new LoginResponse(result.getToken());
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
    public AuthenticationResult authenticateUser(String username,String password){
        User user=userRepository.findByUsername(username);

        if (user!=null){
            if (passwordEncoder.matches(password, user.getPassword())){
                String token = generateToken(username);
                return new AuthenticationResult(true,token);
            }
        }

        return new AuthenticationResult(false,null);

    }

    public String generateToken(String userId) {
        long expirationTime = 3600000; // 1 hour in milliseconds
        String secretKey = tokenConfig.getSecretKey();

        return Jwts.builder()
                .setSubject(userId)
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
                .signWith(SignatureAlgorithm.HS512, secretKey)
                .compact();
    }

    public boolean validateToken(String token) {
        String secretKey = tokenConfig.getSecretKey();

        try {
            Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token);

            return true;
        } catch (Exception e) {
            // Token validation failed
            return false;
        }
    }

    public String getUserIdFromToken(String token) {
        String secretKey = tokenConfig.getSecretKey();

        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            return claims.getSubject();
        } catch (Exception e) {
            // Token validation failed
            return null;
        }
    }
}
