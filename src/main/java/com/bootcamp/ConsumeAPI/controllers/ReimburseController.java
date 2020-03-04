package com.bootcamp.ConsumeAPI.controllers;

import com.bootcamp.ConsumeAPI.entities.UpdateStatusDto;
import com.bootcamp.ConsumeAPI.services.ReimburseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RequestMapping(value = "approval")
@Controller
public class ReimburseController {
    @Autowired
    private ReimburseService reimburseService;

    @GetMapping("")
    public String getAll(Model model, HttpServletRequest request) {
        model.addAttribute("nama",  request.getSession().getAttribute("employee"));
        model.addAttribute("peran",request.getSession().getAttribute("role"));
        model.addAttribute("approvals", reimburseService.getByStatus(request.getSession().getAttribute("id").toString() ));
        return "approval";
    }

    @PostMapping("update/{action}")
    public String updateStatus(HttpServletRequest request, @Valid UpdateStatusDto updateStatusDto,  @PathVariable String action){
        updateStatusDto.setStatus(action);
        updateStatusDto.setEmployeeId(request.getSession().getAttribute("id").toString());
        reimburseService.updateStatus(updateStatusDto);

        return "redirect:/approval";
    }
}
