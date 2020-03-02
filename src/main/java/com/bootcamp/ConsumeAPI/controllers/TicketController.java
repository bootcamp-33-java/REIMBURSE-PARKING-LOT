package com.bootcamp.ConsumeAPI.controllers;

import com.bootcamp.ConsumeAPI.entities.ReimburseDto;
import com.bootcamp.ConsumeAPI.services.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("ticket")
@Controller
public class TicketController {

    @Autowired
    private TicketService ticketService;

    @GetMapping
    public String getAll(Model model) {
        model.addAttribute("tickets", ticketService.getAll());
        return "ticket";
    }

    @PostMapping("save")
    public String save(ReimburseDto reimburseDto){
        ticketService.save(reimburseDto);
        return "";
    }
}
