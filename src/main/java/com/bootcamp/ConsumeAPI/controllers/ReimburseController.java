package com.bootcamp.ConsumeAPI.controllers;

import com.bootcamp.ConsumeAPI.services.ReimburseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServlet;

@RequestMapping(value = "reimburse")
@Controller
public class ReimburseController {
    @Autowired
    private ReimburseService reimburseService;
    @GetMapping("")
    public String getAll(Model model, HttpServlet request){
//        model.addAttribute("approvals", reimburseService.getByStatus(request.getSession().getAttribute("id")));
        return "reimburse";
    }
}
