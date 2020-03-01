package com.bootcamp.ConsumeAPI.services;


import com.bootcamp.ConsumeAPI.entities.History;
import com.bootcamp.ConsumeAPI.repositories.HistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HistoryService {
    @Autowired
    private HistoryRepository historyRepository;

    public void save(History history){
        historyRepository.save(history);
    }
}
