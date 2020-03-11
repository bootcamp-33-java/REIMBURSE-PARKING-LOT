/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bootcamp.ConsumeAPI.controllers;

import com.bootcamp.ConsumeAPI.entities.Employee;
import com.bootcamp.ConsumeAPI.entities.Vehicle;
import com.bootcamp.ConsumeAPI.services.VehicleService;
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

/**
 * @author FIKRI-PC
 */
@RequestMapping(value = "/vehicle")
@Controller
public class VehicleController {

    @Autowired
    VehicleService service;


    @GetMapping("")
    public String getAll(Model model, HttpServletRequest request) {
        model.addAttribute("nama", request.getSession().getAttribute("employee"));
        model.addAttribute("peran", request.getSession().getAttribute("role"));
        model.addAttribute("vehicles", service.getAll(request.getSession().getAttribute("id").toString()));
        return "vehicle";
    }

    @PostMapping("save")
    public String save(@Valid Vehicle vehicle, HttpServletRequest request, @RequestParam("file") MultipartFile file,
                       RedirectAttributes redirectAttributes) throws IOException {

        String id = request.getSession().getAttribute("id").toString();

        String UPLOADED_FOLDER = "G://Bootcamp Mii//SPRING MVC//REIMBURSE-PARKING-LOT//src//main//resources//static//img//vehicle//";

        if (file.isEmpty()) {
            redirectAttributes.addFlashAttribute("message", "Please select a file to upload");
            return "redirect:uploadStatus";
        }


        byte[] bytes = file.getBytes();
        Path path = Paths.get(UPLOADED_FOLDER +id+ file.getOriginalFilename());
        Files.write(path, bytes);


        redirectAttributes.addFlashAttribute("message",
                "You successfully uploaded '" + file.getOriginalFilename() + "'");
        String upload = id + file.getOriginalFilename();


        vehicle.setPhotoStnk(upload);
        vehicle.setEmployee(new Employee(id));
        service.save(vehicle);
        return ("redirect:/vehicle");

    }



    @GetMapping("/{id}")
    public String getById(Model model, @PathVariable("id") String id, HttpServletRequest request) {

        model.addAttribute("vehicle", service.getById(id));
        model.addAttribute("vehicles", service.getAll(request.getSession().getAttribute("id").toString()));
        return "vehicle";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") String id) {

        service.delete(id);
        return ("redirect:/vehicle");
    }

}
