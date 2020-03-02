package com.bootcamp.ConsumeAPI.services;


import com.bootcamp.ConsumeAPI.entities.Employee;
import com.bootcamp.ConsumeAPI.entities.Reimburse;
import com.bootcamp.ConsumeAPI.repositories.ReimburseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ReimburseService {

    @Autowired
    private ReimburseRepository reimburseRepository;

    @Autowired
    private EmployeeService employeeService;

    public Reimburse save(Reimburse reimburse) {

        Optional<Reimburse> optionalReimburse = reimburseRepository.findById(reimburse.getId());
        if (!optionalReimburse.isPresent()) {
            reimburseRepository.save(reimburse);
        } else {
            Reimburse reimburse1 = optionalReimburse.get();
            reimburse1.setCurrentStatus(reimburse.getCurrentStatus());
            Employee employee = new Employee();
            employee.setId(reimburse.getEmployee().getId());
            reimburse1.setEmployee(employee);
            reimburse1.setEndDate(reimburse.getEndDate());
            reimburse1.setNotes(reimburse.getNotes());
            reimburse1.setTotal(reimburse.getTotal());

            reimburseRepository.save(reimburse1);
        }
        return reimburse;
    }

    public Optional<Reimburse> findById(String id) {
        return reimburseRepository.findById(id);
    }

    public List<Reimburse> getByStatus(String statusId) {
        Optional<Employee> optionalReimburse = employeeService.getById(statusId);
        List<Reimburse> reimburseList = new ArrayList<>();

        if (optionalReimburse.get().getRole().equalsIgnoreCase("pic")) {
            reimburseList = reimburseRepository.findByCurrentStatus_Id(1);
        } else if (optionalReimburse.get().getRole().equalsIgnoreCase("hr")) {
            reimburseList = reimburseRepository.findByCurrentStatus_Id(2);
        }
        return reimburseList;
    }

    public Optional<Reimburse> getEmployeeId(String employeeId){
        return reimburseRepository.findByEmployee_Id(employeeId);
    }

}
