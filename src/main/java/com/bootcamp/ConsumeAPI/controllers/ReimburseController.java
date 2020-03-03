package com.bootcamp.ConsumeAPI.controllers;

import com.bootcamp.ConsumeAPI.entities.UpdateStatusDto;
import com.bootcamp.ConsumeAPI.services.ReimburseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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

    @PutMapping("update/{id}/{action}")
    public String updateStatus(HttpServletRequest request, @PathVariable String action, @PathVariable String id){
        UpdateStatusDto updateStatusDto=new UpdateStatusDto();
        updateStatusDto.setStatus(action);
        updateStatusDto.setId(id);
        updateStatusDto.setRole(request.getSession().getAttribute("role").toString());
        reimburseService.updateStatus(updateStatusDto);

        return "redirect:/approval";
    }
}
