/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bootcamp.ConsumeAPI.controllers;

import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author FIKRI-PC
 */
@Controller
public class MainController {
    
    @RequestMapping("/")
    public String home(Model model,HttpServletRequest request){
        model.addAttribute("nama","Hi.. "+ request.getSession().getAttribute("employee"));
    
        return "dashboard";
    }
}
