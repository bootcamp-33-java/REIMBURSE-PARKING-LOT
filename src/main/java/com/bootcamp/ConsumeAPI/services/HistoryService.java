package com.bootcamp.ConsumeAPI.services;


import com.bootcamp.ConsumeAPI.entities.History;
import com.bootcamp.ConsumeAPI.repositories.HistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class HistoryService {
    @Autowired
    private HistoryRepository historyRepository;

    public void save(History history) {
        history.setHistoryDate(LocalDateTime.now());
        historyRepository.save(history);
    }

    public List<History> getAll(String employeeId) {
        return historyRepository.findAllByReimburse_IdStartsWith(employeeId);

    }
}
