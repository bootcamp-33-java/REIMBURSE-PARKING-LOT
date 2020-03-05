package com.bootcamp.ConsumeAPI.services;


import com.bootcamp.ConsumeAPI.entities.*;
import com.bootcamp.ConsumeAPI.repositories.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(rollbackFor = Exception.class)
public class TicketService {

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private ReimburseService reimburseService;

    @Autowired
    private HistoryService historyService;

    @Autowired
    private ParkingLotService parkingLotService;

    @Autowired
    private VehicleService vehicleService;

    @Autowired
    private EmployeeService employeeService;


    public ReimburseDto save(ReimburseDto reimburseDto) {
        LocalDate date=reimburseDto.getTicket().getUploadDate().plusDays(1);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM yyyy");
        String period = date.format(formatter);
        DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("MMyyyy");
        String id = date.format(formatter1);


        Employee employee = employeeService.getById(reimburseDto.getEmployee().getId()).get();

        String idReimburse = employee.getId() + id;
        Optional<Reimburse> optionalReimburse = reimburseService.findById(idReimburse);

        Reimburse reimburse = new Reimburse();
        Status status = new Status();
        status.setId(1);

        if (!optionalReimburse.isPresent()) {
            reimburse.setId(idReimburse);
            reimburse.setStartDate(date);
            reimburse.setEndDate(date);
            reimburse.setEmployee(employee);
            reimburse.setTotal(reimburseDto.getTicket().getPrice());
            reimburse.setPeriod(period);
            reimburse.setCurrentStatus(status);


        } else {
            reimburse = optionalReimburse.get();
            reimburse.setEndDate(date);
            reimburse.setTotal(reimburse.getTotal() + reimburseDto.getTicket().getPrice());

        }
        reimburse.setNotes("nothing");
        reimburseService.save(reimburse);

        reimburseDto.getTicket().setId(0);
        reimburseDto.getTicket().setReimburse(reimburse);

        Ticket ticket = reimburseDto.getTicket();
        ticket.setUploadDate(date);
        ticket.setPhotoTicket("photo");
        ticketRepository.save(ticket);

        History history = new History();
        history.setId(0);
        history.setReimburse(reimburse);
        history.setApprovalBy(employee.getSite().getPic());
        history.setNotes(reimburse.getNotes());
        history.setStatus(status);

        historyService.save(history);


        return reimburseDto;
    }

    public Ticket update(Ticket ticket) {
        Optional<Ticket> optionalTicket = ticketRepository.findById(ticket.getId());
        if (!optionalTicket.isPresent()) {
            throw new RuntimeException("Data Not Found");
        } else {
            Ticket ticket1 = optionalTicket.get();
            ticket1.setPrice(ticket.getPrice());

            ParkingLot parkingLot = new ParkingLot();
            parkingLot.setId(ticket.getParking().getId());
            ticket1.setParking(parkingLot);

            Vehicle vehicle = new Vehicle();
            vehicle.setId(ticket.getVehicle().getId());
            ticket1.setVehicle(vehicle);

            Reimburse reimburse = new Reimburse();
            reimburse.setId(ticket.getReimburse().getId());
            ticket1.setReimburse(reimburse);

            ticket1.setUploadDate(ticket1.getUploadDate());
            ticket1.setPhotoTicket(ticket1.getPhotoTicket());

            ticketRepository.save(ticket1);

        }

        return ticket;
    }

    public List<Ticket> getAll(String employeeId) {
        return ticketRepository.findAllByReimburse_IdStartingWith(employeeId);
    }

    public List<ParkingLot> getAllParkingLot() {
        return parkingLotService.getAll();
    }

    public List<Vehicle> getAllVehicle(String employeeId) {
        return vehicleService.getAll(employeeId);
    }


}
