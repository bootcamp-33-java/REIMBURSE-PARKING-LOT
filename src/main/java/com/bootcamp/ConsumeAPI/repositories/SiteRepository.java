package com.bootcamp.ConsumeAPI.repositories;

import com.bootcamp.ConsumeAPI.entities.Site;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SiteRepository extends PagingAndSortingRepository<Site, String> {
    List<Site> findAll();
}
