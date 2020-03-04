package com.bootcamp.ConsumeAPI.repositories;

import com.bootcamp.ConsumeAPI.entities.Ticket;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TicketRepository extends PagingAndSortingRepository<Ticket, Integer> {
    List<Ticket> findAllByReimburse_IdContaining(String employeeId);

    Optional<Ticket> findById(Integer id);
}
