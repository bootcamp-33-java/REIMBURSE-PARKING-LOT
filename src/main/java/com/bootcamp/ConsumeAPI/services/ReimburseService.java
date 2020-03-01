package com.bootcamp.ConsumeAPI.services;


import com.bootcamp.ConsumeAPI.entities.Reimburse;
import com.bootcamp.ConsumeAPI.repositories.ReimburseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ReimburseService {

    @Autowired
    private ReimburseRepository reimburseRepository;

    public Reimburse save(Reimburse reimburse) {

        Optional<Reimburse> optionalReimburse = reimburseRepository.findById(reimburse.getId());
        if (!optionalReimburse.isPresent()) {
            reimburseRepository.save(reimburse);
        } else {
            Reimburse reimburse1 = optionalReimburse.get();
            reimburse1.setCurrentStatus(reimburse.getCurrentStatus());
//            Employee employee=reimburse.getEmployee();
            reimburse1.setEmployee(reimburse.getEmployee());
            reimburse1.setEndDate(reimburse.getEndDate());
            reimburse1.setNotes(reimburse.getNotes());
            reimburse1.setTotal(reimburse.getTotal());

            reimburseRepository.save(reimburse1);
        }
        return reimburse;
    }

    public Optional<Reimburse> findById(String id){
        return reimburseRepository.findById(id);
    }
}
