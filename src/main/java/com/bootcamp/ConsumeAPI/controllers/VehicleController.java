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

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

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
    public String save(@Valid Vehicle vehicle, HttpServletRequest request) {
        vehicle.setEmployee(new Employee(request.getSession().getAttribute("id").toString()));
        service.save(vehicle);
        return ("redirect:/vehicle");
    }

    @PostMapping("update")
    public String update( @Valid Vehicle vehicle, HttpServletRequest request) {
        vehicle.setEmployee(new Employee(request.getSession().getAttribute("id").toString()));
        service.update(vehicle);
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
