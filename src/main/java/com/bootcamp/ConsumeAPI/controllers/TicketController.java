package com.bootcamp.ConsumeAPI.controllers;

import com.bootcamp.ConsumeAPI.entities.ReimburseDto;
import com.bootcamp.ConsumeAPI.entities.Ticket;
import com.bootcamp.ConsumeAPI.services.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@RequestMapping(value = "ticket")
@Controller
public class TicketController {

    @Autowired
    private TicketService ticketService;

    @GetMapping
    public String getAll(Model model, HttpServletRequest request) {
        model.addAttribute("nama", request.getSession().getAttribute("id"));
        model.addAttribute("peran",request.getSession().getAttribute("role"));
        model.addAttribute("parkLots", ticketService.getAllParkingLot());
        model.addAttribute("vehicles", ticketService.getAllVehicle(request.getSession().getAttribute("id").toString()));
        model.addAttribute("tickets", ticketService.getAll(request.getSession().getAttribute("id").toString()));
        return "ticket";
    }

    @PostMapping("save")
    public String save(ReimburseDto reimburseDto, HttpServletRequest request) {
        reimburseDto.setEmployeeId(request.getSession().getAttribute("id").toString());
        Ticket ticket1=reimburseDto.getTicket();
        ticket1.setPhotoTicket("photo");
        reimburseDto.setTicket(ticket1);
        ticketService.save(reimburseDto);
        return "redirect:/ticket";
    }

    @PutMapping("update")
    public String update(Ticket ticketEntity) {
        ticketService.update(ticketEntity);

        return "redirect:/ticket";
    }
}
