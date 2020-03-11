package com.bootcamp.ConsumeAPI.controllers;

import com.bootcamp.ConsumeAPI.entities.Employee;
import com.bootcamp.ConsumeAPI.entities.ReimburseDto;
import com.bootcamp.ConsumeAPI.entities.Ticket;
import com.bootcamp.ConsumeAPI.services.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RequestMapping(value = "/ticket")
@Controller
public class TicketController {

    @Autowired
    private TicketService ticketService;

    @GetMapping("")
    public String getAll(Model model, HttpServletRequest request) {
        model.addAttribute("nama", request.getSession().getAttribute("employee"));
        model.addAttribute("peran", request.getSession().getAttribute("role"));
        model.addAttribute("parkLots", ticketService.getAllParkingLot());
        model.addAttribute("vehicles", ticketService.getAllVehicle(request.getSession().getAttribute("id").toString()));
        model.addAttribute("tickets", ticketService.getAll(request.getSession().getAttribute("id").toString()));
        System.out.println();
        return "ticket";
    }

    @PostMapping("save")
    public String save(@Valid Ticket ticket, HttpServletRequest request, @RequestParam("file") MultipartFile file,
                       RedirectAttributes redirectAttributes) throws IOException {

        String id = request.getSession().getAttribute("id").toString();

        String UPLOADED_FOLDER = "G://Bootcamp Mii//SPRING MVC//REIMBURSE-PARKING-LOT//src//main//resources//static//img//ticket//";

        if (file.isEmpty()) {
            redirectAttributes.addFlashAttribute("message", "Please select a file to upload");
            return "redirect:uploadStatus";
        }


        byte[] bytes = file.getBytes();
        Path path = Paths.get(UPLOADED_FOLDER +id+file.getOriginalFilename());
        Files.write(path, bytes);

        redirectAttributes.addFlashAttribute("message",
                "You successfully uploaded '" + file.getName() + "'");

        String upload = id+file.getOriginalFilename();
        ReimburseDto reimburseDto = new ReimburseDto();
        reimburseDto.setEmployee(new Employee(id));
        ticket.setPhotoTicket(upload);
        reimburseDto.setTicket(ticket);
        ticketService.save(reimburseDto);
        return ("redirect:/ticket");
    }

    @GetMapping("getId/{id}")
    public String getById(Model model,@PathVariable String id){
        model.addAttribute("vehicle", ticketService.getById(Integer.parseInt(id)));
        return "redirect:/";
    }

}
