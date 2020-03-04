package com.bootcamp.ConsumeAPI.repositories;


import com.bootcamp.ConsumeAPI.entities.Employee;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends PagingAndSortingRepository<Employee, String> {
    List<Employee> findAll();
}
