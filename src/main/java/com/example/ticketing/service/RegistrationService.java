package com.example.ticketing.service;

import com.example.ticketing.repository.UserRepository;
import com.example.ticketing.user.User;
import com.example.ticketing.user.UserRegistrationRequest;
import com.example.ticketing.user.UserRole;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RegistrationService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public RegistrationService(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = new BCryptPasswordEncoder();
    }

    //testing only
    public List<User> getUsers(){
       return userRepository.findAll();
    }


    public ResponseEntity<String> register(UserRegistrationRequest userRegistrationRequest) {
        if (userRepository.existsByUsername(userRegistrationRequest.getUsername())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Username already taken");
        }
        User newUser = new User();
        newUser.setUsername(userRegistrationRequest.getUsername());
        newUser.setPassword(bCryptPasswordEncoder.encode(userRegistrationRequest.getPassword()));
        newUser.setUserRole(UserRole.EXTERNAL);

        userRepository.save(newUser);

        return ResponseEntity.ok("Registration successful");
    }

    public ResponseEntity<String> setUserRole(String username, String userRole) {
        UserRole userRole1;
        switch (userRole){
            case "admin":
                userRole1=UserRole.ADMIN;
                break;
            case "internal":
                userRole1=UserRole.INTERNAL;
                break;
            default:
                userRole1=UserRole.EXTERNAL;
                break;
        }

        User user = userRepository.findByUsername(username);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        user.setUserRole(userRole1);
        userRepository.save(user);
        return ResponseEntity.ok("User role set");
    }

}
