package com.example.ticketing.controller;


import com.example.ticketing.service.IdAPIService;
import org.dto.PersonDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

// temporary for testing purposes only
@RestController
@RequestMapping(path = "api/idprovider")
public class ApiCallerController {
    @Autowired
    private IdAPIService idAPIService;

    @GetMapping()
    public List<PersonDTO> callPersonApi() {
        return idAPIService.callIdProviderApi();
    }

    @GetMapping("/{id}")
    public PersonDTO getPersonById(@PathVariable String id) {
        return idAPIService.getPersonById(id);
    }

    @GetMapping("/name/{name}")
    public PersonDTO getPersonByName(@PathVariable String name) {
        return idAPIService.getPersonByName(name);
    }

    @GetMapping("/wide/{param}")
    public List<PersonDTO> getPersonWideSearch(@PathVariable String param) {
        return idAPIService.getPersonWideSearch(param);
    }
}
