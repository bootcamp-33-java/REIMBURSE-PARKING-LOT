package com.bootcamp.ConsumeAPI.repositories;

import com.bootcamp.ConsumeAPI.entities.Reimburse;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReimburseRepository extends PagingAndSortingRepository<Reimburse, String> {
    List<Reimburse> findAll();

    Optional<Reimburse> findById(String id);

    List<Reimburse> findByCurrentStatus_Id(Integer statusId);

}
