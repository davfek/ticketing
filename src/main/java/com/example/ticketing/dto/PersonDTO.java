package com.example.ticketing.dto;


import com.fasterxml.jackson.annotation.JsonProperty;

public class PersonDTO{
    @JsonProperty("id")
    String id;
    @JsonProperty("name")
    String name;
    String phoneNumber;
    String email;


    InternalTeam internalTeam=InternalTeam.NON_APPLICABLE;

    boolean isManager=false;
    String company="n/a";

    public PersonDTO() {
    }

    public PersonDTO(String id, String name, String phoneNumber, String email) {
        this.id = id;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;

    }

    public PersonDTO(String id, String name, String phoneNumber, String email, InternalTeam internalTeam, boolean isManager) {
        this.id = id;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.internalTeam = internalTeam;
        this.isManager = isManager;
    }

    public PersonDTO(String id, String name, String phoneNumber, String email, String company) {
        this.id = id;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.company = company;
    }
}
