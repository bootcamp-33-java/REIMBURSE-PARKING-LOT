/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bootcamp.ConsumeAPI.services;

import com.bootcamp.ConsumeAPI.entities.Employee;
import com.bootcamp.ConsumeAPI.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Insane
 */

@Service
public class EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;
    
    public Employee getById(String id){
        Employee employee=employeeRepository.findById(id).get();
        
        return employee;
        
    }
    
    public Employee save(Employee employee){
        employeeRepository.save(employee);
        
        return employee;
    }
    
}
