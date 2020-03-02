/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bootcamp.ConsumeAPI.services;

import com.bootcamp.ConsumeAPI.entities.Vehicle;
import com.bootcamp.ConsumeAPI.repositories.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


/**
 *
 * @author FIKRI-PC
 */
@Service
public class VehicleService {
        @Autowired
        VehicleRepository repository;

    public List<Vehicle> getAll() {
        return repository.findAll();
    }

    public Vehicle save(Vehicle vehicle) {
        return repository.save(vehicle);
    }
    public void delete(String id) {
        repository.deleteById(id);
        
    }
    public Optional<Vehicle> getById(String id) {
        return repository.findById(id);
    }
}
