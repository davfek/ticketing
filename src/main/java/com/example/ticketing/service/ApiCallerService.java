package com.example.ticketing.service;


import com.example.ticketing.dto.PersonDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;

@Service
public class ApiCallerService {
    @Value("${docker.api.url}")
    private String dockerApiUrl;

    public List<PersonDTO> callIdProviderApi(){
        RestTemplate restTemplate=new RestTemplate();
        String apiUrl=dockerApiUrl+"api/v1/person";
        try {
            ResponseEntity<List<PersonDTO>> response=restTemplate.exchange(apiUrl, HttpMethod.GET, null, new ParameterizedTypeReference<List<PersonDTO>>() {
            });

            if (response.getStatusCode()== HttpStatus.OK){
                return response.getBody();
            }else {
                System.err.println("API call failed with code: "+response.getStatusCode());
                return Collections.emptyList();
            }
        }catch (HttpClientErrorException ex){
            System.err.println("API call failed with code: "+ex.getStatusCode());
            return Collections.emptyList();
        }catch (Exception e){
            e.printStackTrace();
            return Collections.emptyList();
        }
    }
}
