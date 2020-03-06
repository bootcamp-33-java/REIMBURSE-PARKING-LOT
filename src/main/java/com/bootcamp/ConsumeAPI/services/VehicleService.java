/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bootcamp.ConsumeAPI.services;

import com.bootcamp.ConsumeAPI.entities.Employee;
import com.bootcamp.ConsumeAPI.entities.Vehicle;
import com.bootcamp.ConsumeAPI.repositories.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


/**
 * @author FIKRI-PC
 */
@Service
public class VehicleService {
    @Autowired
    VehicleRepository vehicleRepository;

    public List<Vehicle> getAll(String employeeId) {
        return vehicleRepository.findAllByEmployee_Id(employeeId);
    }

    public Vehicle save(Vehicle vehicle) {
        String id = vehicle.getId();
        String target = ",";
        String idNew = id.replace(target, " ").toUpperCase();

        vehicle.setId(idNew);
        vehicle.setPhotoStnk("poto");
        return vehicleRepository.save(vehicle);
    }

    public Vehicle update(Vehicle vehicle) {
        Optional<Vehicle> optionalVehicle = vehicleRepository.findById(vehicle.getId());
        if (optionalVehicle.isPresent()) {
            Vehicle vehicle1 = optionalVehicle.get();
            vehicle1.setStnkOwner(vehicle.getStnkOwner());
            vehicle1.setPhotoStnk(vehicle.getPhotoStnk());
            vehicle1.setEmployee(new Employee(vehicle.getEmployee().getId()));
            vehicle1.setVehicleType(vehicle.getVehicleType());
            vehicleRepository.save(vehicle1);
        }
        return vehicle;
    }

    public void delete(String id) {
        vehicleRepository.deleteById(id);

    }

    public Optional<Vehicle> getById(String id) {
        return vehicleRepository.findById(id);
    }
}
