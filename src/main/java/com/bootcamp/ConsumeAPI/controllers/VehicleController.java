/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bootcamp.ConsumeAPI.controllers;

import com.bootcamp.ConsumeAPI.entities.Employee;
import com.bootcamp.ConsumeAPI.entities.Vehicle;
import com.bootcamp.ConsumeAPI.services.VehicleService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author FIKRI-PC
 */
@RequestMapping(value = "vehicle")
@Controller
public class VehicleController {

    @Autowired
    VehicleService service;


    @GetMapping("")
    public String getAll(Model model, HttpServletRequest request){
        model.addAttribute("nama","Hi.. "+ request.getSession().getAttribute("employee"));
        model.addAttribute("vehicles",service.getAll(request.getSession().getAttribute("id").toString())) ;
          return "vehicle";
    }

    @PostMapping("save")
    public String save(@Valid Vehicle vehicle,HttpServletRequest request) {
        vehicle.setPhotoStnk("poto");
        Employee employee = new Employee();
        employee.setId(request.getSession().getAttribute("id").toString());
        vehicle.setEmployee(employee);
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
