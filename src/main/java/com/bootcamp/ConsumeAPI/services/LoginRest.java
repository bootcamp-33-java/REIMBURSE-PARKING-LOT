/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bootcamp.ConsumeAPI.services;

import com.bootcamp.ConsumeAPI.entities.Employee;
import com.bootcamp.ConsumeAPI.entities.EmployeeLogin;
import com.bootcamp.ConsumeAPI.entities.LoginData;
import com.bootcamp.ConsumeAPI.repositories.LoginRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author FIKRI-PC
 */
@Service
public class LoginRest {

   @Value("${data.api.url}")
    private String url;
    
    @Value("${data.api.key}")
    private String key;
    private static final RestTemplate restTemplate = new RestTemplate();
    
    @Autowired
    LoginRepository repository;

    private HttpHeaders getHeaders() {
        return new HttpHeaders() {
            {
                
                set("Authorization", key);
                setContentType(MediaType.APPLICATION_JSON);
            }
        };
    }

    public EmployeeLogin login(LoginData logindata) {
        HttpEntity<LoginData> request = new HttpEntity(logindata, getHeaders());

        ResponseEntity<EmployeeLogin> responseEntity = restTemplate.exchange(
                url + "/login",
                HttpMethod.POST,
                request,
                new ParameterizedTypeReference<EmployeeLogin>() {
        }
        );
        EmployeeLogin result = responseEntity.getBody();

        return result;
    }
    
}
