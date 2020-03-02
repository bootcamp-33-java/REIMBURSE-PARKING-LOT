package com.bootcamp.ConsumeAPI.services;


import com.bootcamp.ConsumeAPI.entities.History;
import com.bootcamp.ConsumeAPI.entities.Reimburse;
import com.bootcamp.ConsumeAPI.repositories.HistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class HistoryService {
    @Autowired
    private HistoryRepository historyRepository;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private ReimburseService reimburseService;

    public void save(History history) {
        historyRepository.save(history);
    }

    public List<History> getAll(String employeeId) {
        Optional<Reimburse> optionalReimburse = reimburseService.getEmployeeId(employeeId);

        List<History> historyList = historyRepository.findAllByReimburseId(optionalReimburse.get().getId());

        return historyList;
    }
}
