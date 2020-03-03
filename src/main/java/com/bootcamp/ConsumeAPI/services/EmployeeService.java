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

import java.util.Optional;

/**
 * @author Insane
 */

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    public Optional<Employee> getById(String id) {
        return employeeRepository.findById(id);


    }

    public Employee save(Employee employee) {
        Optional<Employee> optionalEmployee = employeeRepository.findById(employee.getId());

        if (!optionalEmployee.isPresent()) {
            employeeRepository.save(employee);
        } else {
            Employee employee1 = optionalEmployee.get();
            employee1.setRole(employee.getRole());

            employeeRepository.save(employee1);
        }

        return employee;
    }

    public Optional<Employee> getRole(String role){
        return employeeRepository.findByRole(role);
    }

}
