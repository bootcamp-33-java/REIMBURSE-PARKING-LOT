package com.bootcamp.ConsumeAPI.services;

import com.bootcamp.ConsumeAPI.entities.ParkingLot;
import com.bootcamp.ConsumeAPI.repositories.ParkingLotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ParkingLotService {
    @Autowired
    private ParkingLotRepository parkingLotRepository;

    public List<ParkingLot> getAll(){
        return parkingLotRepository.findAll();
    }
}
