package com.bootcamp.ConsumeAPI.controllers;

import com.bootcamp.ConsumeAPI.services.HistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@RequestMapping(value = "history")
public class HistoryController {
    @Autowired
    private HistoryService historyService;

    public String getAll(Model model, HttpServletRequest request){
        model.addAttribute("nama","Hi.. "+ request.getSession().getAttribute("employee"));
        model.addAttribute("histories",historyService.getAll(request.getSession().getAttribute("id").toString())) ;
        return "history";
    }
}
