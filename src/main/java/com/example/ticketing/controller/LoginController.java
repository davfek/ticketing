package com.example.ticketing.controller;

import com.example.ticketing.service.LoginService;
import com.example.ticketing.user.LoginRequest;
import com.example.ticketing.user.LoginResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/login")
public class LoginController {
    private final LoginService loginService;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public LoginController(LoginService loginService, AuthenticationManager authenticationManager){
        this.loginService=loginService;
        this.authenticationManager = authenticationManager;
    }


    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<LoginResponse> loginUser(@RequestBody LoginRequest loginRequest){
        LoginResponse loginResponse= loginService.login(loginRequest);
        if (loginResponse!=null){
            return new ResponseEntity<>(loginResponse, HttpStatus.OK);
        }else return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
