package com.bootcamp.ConsumeAPI.services;


import com.bootcamp.ConsumeAPI.entities.*;
import com.bootcamp.ConsumeAPI.repositories.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

@Service
@Transactional
public class TicketService {

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private ReimburseService reimburseService;

    @Autowired
    private HistoryService historyService;

    public ReimburseDto save(ReimburseDto reimburseDto) {
        SimpleDateFormat frmt = new SimpleDateFormat("MMMM yyyy");
        String period = frmt.format(reimburseDto.getTicket().getUploadDate());
        SimpleDateFormat formt = new SimpleDateFormat("MMyyyy");
        String id = formt.format(reimburseDto.getTicket().getUploadDate());

        String idReimburse = reimburseDto.getEmployeeId() + id;

        Optional<Reimburse> optionalReimburse = reimburseService.findById(idReimburse);

        if (!optionalReimburse.isPresent()) {
            Reimburse reimburse = new Reimburse();
            reimburse.setId(idReimburse);
            reimburse.setStartDate(reimburseDto.getTicket().getUploadDate());
            reimburse.setEndDate(reimburseDto.getTicket().getUploadDate());
            Employee employee = new Employee();
            employee.setId(reimburseDto.getEmployeeId());
            reimburse.setEmployee(employee);
            reimburse.setTotal(reimburseDto.getTicket().getPrice());
            reimburse.setPeriod(period);
            Status status = new Status();
            status.setId(5);
            reimburse.setCurrentStatus(status);


            reimburseService.save(reimburse);
        } else {
            Reimburse reimburse = optionalReimburse.get();
            reimburse.setEndDate(reimburseDto.getTicket().getUploadDate());
            reimburse.setTotal(reimburse.getTotal() + reimburseDto.getTicket().getPrice());

            reimburseService.save(reimburse);
        }

        Ticket ticket = reimburseDto.getTicket();

        Reimburse reimburse = new Reimburse();
        reimburse.setId(idReimburse);
        ticket.setReimburse(reimburse);

        ticketRepository.save(ticket);


        History history=new History();
        Employee employee=new Employee();
        employee.setId(reimburseDto.getEmployeeId());
        history.setApprovalBy(employee);
        history.setHistoryDate(new Date());
        history.setNotes("");

        Status status = new Status();
        status.setId(5);
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
}
