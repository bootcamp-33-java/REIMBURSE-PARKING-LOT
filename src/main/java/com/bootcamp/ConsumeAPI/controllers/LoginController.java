/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bootcamp.ConsumeAPI.controllers;

import com.bootcamp.ConsumeAPI.entities.Employee;
import com.bootcamp.ConsumeAPI.entities.EmployeeLogin;
import com.bootcamp.ConsumeAPI.entities.LoginData;
import com.bootcamp.ConsumeAPI.entities.Site;
import com.bootcamp.ConsumeAPI.services.EmployeeService;
import com.bootcamp.ConsumeAPI.services.LoginRest;
import com.squareup.okhttp.Request;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import javafx.print.Collation;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author FIKRI-PC
 */
@Controller
public class LoginController {

    @Autowired
    private LoginRest rest;
    
    @Autowired
    private EmployeeService employeeService;
    

    @GetMapping("login")
    public String handlingLog() {
        String result = "";
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!auth.getName().equalsIgnoreCase("anonymousUser")) {
            result = "redirect:/";
        } else {
            result = "login";
        }
        return result;
    }

    @PostMapping("verification")
    public String verification(@Valid LoginData loginData, RedirectAttributes redirect, HttpServletRequest request) {
        String result;

        EmployeeLogin employeeLogin = rest.login(loginData);

        if (employeeLogin.getStatus().equalsIgnoreCase("Login Success")) {
//            ambil semua role
            User user = new User(employeeLogin.getEmployee().getId(), "", getAuthorities(employeeLogin));
//            diberi akses
            PreAuthenticatedAuthenticationToken authentication = new PreAuthenticatedAuthenticationToken(user, "", user.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
            try {
                Employee employee = new Employee();
                employee.setId(employeeLogin.getEmployee().getId());
                employee.setEmail(employeeLogin.getEmployee().getEmail());
                employee.setActive(true);
                employee.setName(employeeLogin.getEmployee().getFirstName() +" " +employeeLogin.getEmployee().getLastName() );
                    employeeService.save(employee); //send ke database local
                request.getSession().setAttribute("employee", employeeLogin.getEmployee().getFirstName() + " "
                        + employeeLogin.getEmployee().getLastName());
                request.getSession().setAttribute("role", employeeLogin.getEmployee().getRoles());
                request.getSession().setAttribute("id", employeeLogin.getEmployee().getId());
                System.out.println("NAMA : " + request.getSession().getAttribute("employee"));
                System.out.println("ROLE : " + request.getSession().getAttribute("role"));
                System.out.println("ID : " + request.getSession().getAttribute("id"));

            } catch (Exception e) {
                System.out.println(e);
            }
            result = "redirect:/";
        } else {
            result = "redirect:/login";
        }
        return result;
    }

    private static Collection<? extends GrantedAuthority> getAuthorities(EmployeeLogin employeeLogin) {
        final List<SimpleGrantedAuthority> authorities = new LinkedList<>();
        for (String role : employeeLogin.getEmployee().getRoles()) {
            authorities.add(new SimpleGrantedAuthority(role));
        }
        return authorities;
    }

}
