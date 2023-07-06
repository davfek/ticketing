package com.example.ticketing.controller;

import com.example.ticketing.service.RegistrationService;
import com.example.ticketing.user.User;
import com.example.ticketing.user.UserRegistrationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<List<User>> getUsers(){
        return new ResponseEntity<>(registrationService.getUsers(),HttpStatus.OK);
    }


    @PostMapping
    public ResponseEntity<String> registerUser(@RequestBody UserRegistrationRequest userRegistrationRequest){
        String response =  registrationService.register(userRegistrationRequest);
        if (response.equals("Registration successful")){
            return new ResponseEntity<>("Registration successful", HttpStatus.CREATED);
        }else return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    //testing purpose only
    @PutMapping("/{username}/{newRole}")
    public ResponseEntity<String> changeUserRole(@PathVariable String username,
                                                  @PathVariable String newRole){
        String response= registrationService.setUserRole(username, newRole);
        if (response.equals("User role set")){
            return new ResponseEntity<>(response,HttpStatus.OK);
        }else return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
