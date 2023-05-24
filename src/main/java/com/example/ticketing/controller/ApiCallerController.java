package com.example.ticketing.controller;


import com.example.ticketing.dto.PersonDTO;
import com.example.ticketing.service.ApiCallerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "api/idprovider")
public class ApiCallerController {
    @Autowired
    private ApiCallerService apiCallerService;

    @GetMapping()
    public List<PersonDTO> callPersonApi(){
        return apiCallerService.callIdProviderApi();
    }
    @GetMapping("/{id}")
    public PersonDTO getPersonById(@PathVariable String id){
        return apiCallerService.getPersonById(id);
    }
    @GetMapping("/name/{name}")
    public PersonDTO getPersonByName(@PathVariable String name){
        return apiCallerService.getPersonByName(name);
    }

    @GetMapping("/wide/{param}")
    public List<PersonDTO> getPersonWideSearch(@PathVariable String param){
        return apiCallerService.getPersonWideSearch(param);
    }
}
