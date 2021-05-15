package com.example.demo.login.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.login.domain.model.UserDetailsImpl;

@Controller
public class HomeController {

/*
    @GetMapping("/home")
    public String getHome(
    		@AuthenticationPrincipal UserDetailsImpl userDetailsImpl,
    		Model model) {
        model.addAttribute("userName", userDetailsImpl.getName());
        model.addAttribute("role", userDetailsImpl.getRole());
        model.addAttribute("contents", "login/home :: home_contents");
        return "login/homeLayout";
    }
*/
    @PostMapping("/logout")
    public String postLogout() {
        return "redirect:/login";
    }

    @GetMapping("/admin")
    public String getAdmin(
    		@AuthenticationPrincipal UserDetailsImpl userDetailsImpl,
    		Model model) {
        model.addAttribute("userName", userDetailsImpl.getName());
        model.addAttribute("role", userDetailsImpl.getRole());
        model.addAttribute("contents", "login/admin :: admin_contents");
        return "login/homeLayout";
    }
}
