package com.bootcamp.ConsumeAPI.services;


import com.bootcamp.ConsumeAPI.entities.*;
import com.bootcamp.ConsumeAPI.repositories.ReimburseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(rollbackFor = Exception.class)
public class ReimburseService {

    @Autowired
    private ReimburseRepository reimburseRepository;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private HistoryService historyService;

    public Reimburse save(Reimburse reimburse) {
        Employee employee = new Employee(reimburse.getEmployee().getId());
        Optional<Reimburse> optionalReimburse = reimburseRepository.findById(reimburse.getId());
        if (!optionalReimburse.isPresent()) {
            reimburseRepository.save(reimburse);
        } else {
            Reimburse reimburse1 = optionalReimburse.get();
            reimburse1.setCurrentStatus(reimburse.getCurrentStatus());
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

        if (optionalReimburse.get().getRole().equalsIgnoreCase("Trainer") || optionalReimburse.get().getRole().equalsIgnoreCase("manager")) {
            reimburseList = reimburseRepository.findByCurrentStatus_Id(1);
        } else if (optionalReimburse.get().getRole().equalsIgnoreCase("Admin")) {
            reimburseList = reimburseRepository.findByCurrentStatus_Id(2);
        }
        return reimburseList;
    }

    public Reimburse updateStatus(UpdateStatusDto updateStatusDto) {
        Reimburse reimburse = new Reimburse();
        Optional<Reimburse> optionalReimburse = reimburseRepository.findById(updateStatusDto.getId());

        Optional<Employee> optionalEmployee = employeeService.getById(updateStatusDto.getEmployeeId());

        if (optionalReimburse.isPresent()) {
            reimburse = optionalReimburse.get();

            Status status = new Status();

            History history = new History();
            history.setId(0);
            history.setNotes(updateStatusDto.getNotes());
            history.setReimburse(reimburse);
            history.setApprovalBy(optionalEmployee.get());

            if (optionalEmployee.isPresent()) {
                if (updateStatusDto.getStatus().equalsIgnoreCase("approved")) {
                    if (optionalEmployee.get().getRole().equalsIgnoreCase("Trainer")) {
                        status.setId(5);
                        reimburse.setCurrentStatus(status);
                        reimburse.setNotes(updateStatusDto.getNotes());
                        reimburseRepository.save(reimburse);

                        history.setStatus(status);
                        historyService.save(history);

                        status.setId(2);
                        reimburse.setCurrentStatus(status);
                        reimburseRepository.save(reimburse);

                    } else if (optionalEmployee.get().getRole().equalsIgnoreCase("admin")) {
                        status.setId(6);
                        reimburse.setCurrentStatus(status);
                        reimburse.setNotes(updateStatusDto.getNotes());
                        reimburseRepository.save(reimburse);
                    }
                } else if (updateStatusDto.getStatus().equalsIgnoreCase("reject")) {
                    if (optionalEmployee.get().getRole().equalsIgnoreCase("trainer")) {
                        status.setId(3);
                        reimburse.setCurrentStatus(status);
                        reimburse.setNotes(updateStatusDto.getNotes());
                        reimburseRepository.save(reimburse);
                    } else if (optionalEmployee.get().getRole().equalsIgnoreCase("admin")) {
                        status.setId(4);
                        reimburse.setCurrentStatus(status);
                        reimburse.setNotes(updateStatusDto.getNotes());
                        reimburseRepository.save(reimburse);
                    }
                }
            }

            history.setStatus(status);
            historyService.save(history);

        }
        return reimburse;
    }

}
