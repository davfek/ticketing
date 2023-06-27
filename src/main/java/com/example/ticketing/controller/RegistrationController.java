package com.example.ticketing.controller;

import com.example.ticketing.service.RegistrationService;
import com.example.ticketing.user.User;
import com.example.ticketing.user.UserRegistrationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/register")
public class RegistrationController {
    private final RegistrationService registrationService;
    @Autowired
    public  RegistrationController(RegistrationService registrationService){
        this.registrationService=registrationService;
    }

    //Testing only
    @GetMapping
    public List<User> getUsers(){
        return registrationService.getUsers();
    }


    @PostMapping
    public ResponseEntity<String> registerUser(@RequestBody UserRegistrationRequest userRegistrationRequest){
        return registrationService.register(userRegistrationRequest);
    }

    //testing purpose only
    @PutMapping("/{username}/{newRole}")
    public ResponseEntity<String> changeUserRole(@PathVariable String username,
                                                  @PathVariable String newRole){
        return registrationService.setUserRole(username, newRole);
    }

}
