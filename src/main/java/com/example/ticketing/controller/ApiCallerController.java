package com.example.ticketing.controller;


import com.example.ticketing.dto.PersonDTO;
import com.example.ticketing.service.ApiCallerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ApiCallerController {
    @Autowired
    private ApiCallerService apiCallerService;

    @GetMapping("/getPerson")
    public List<PersonDTO> callPersonApi(){
        return apiCallerService.callIdProviderApi();
    }
}
