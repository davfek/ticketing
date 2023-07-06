package com.example.ticketing.service;

import com.example.ticketing.repository.UserRepository;
import com.example.ticketing.user.User;
import com.example.ticketing.user.UserRegistrationRequest;
import com.example.ticketing.user.UserRole;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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


    public String register(UserRegistrationRequest userRegistrationRequest) {
        if (userRepository.existsByUsername(userRegistrationRequest.getUsername())) {
            return "Username already taken";
        }
        User newUser = new User();
        newUser.setUsername(userRegistrationRequest.getUsername());
        newUser.setPassword(bCryptPasswordEncoder.encode(userRegistrationRequest.getPassword()));
        newUser.setUserRole(UserRole.EXTERNAL);

        userRepository.save(newUser);

        return "Registration successful";
    }

    public String setUserRole(String username, String userRole) {
        UserRole userRole1=UserRole.defineRoleFromString(userRole);
        Optional<User> user = userRepository.findByUsername(username);

        if (user.isEmpty()) {
            return "User not found";
        }

        user.get().setUserRole(userRole1);
        userRepository.save(user.get());
        return "User role set";
    }

}
