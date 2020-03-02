package com.bootcamp.ConsumeAPI.repositories;

import com.bootcamp.ConsumeAPI.entities.History;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HistoryRepository extends PagingAndSortingRepository<History, Integer> {
    List<History> findAllByReimburseId(String reimburseId);
}
