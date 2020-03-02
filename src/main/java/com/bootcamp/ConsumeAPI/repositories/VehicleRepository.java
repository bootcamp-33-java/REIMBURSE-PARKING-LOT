package com.bootcamp.ConsumeAPI.repositories;

import com.bootcamp.ConsumeAPI.entities.Vehicle;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VehicleRepository extends PagingAndSortingRepository<Vehicle, String> {
    List<Vehicle> findAll();
  
    Optional<Vehicle> findById(String id);
}
