package com.example.demo.login.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.demo.login.domain.model.LendingView;
import com.example.demo.login.domain.model.UserDetailsImpl;
//import com.example.demo.login.domain.model.LendingRegistForm;
import com.example.demo.login.domain.service.LendingService;

@Controller
public class HomeLendingStatusController {
    @Autowired
    LendingService lendingService;

    @GetMapping("/lendingStatus")
    public String getLendingList(
    		@AuthenticationPrincipal UserDetailsImpl userDetailsImpl,
    		Model model) {
        model.addAttribute("userName", userDetailsImpl.getName());
        model.addAttribute("role", userDetailsImpl.getRole());
        model.addAttribute("contents", "login/lendingStatus :: lendingStatus_contents");

        List<LendingView> lendingViewList =
        		lendingService.selectUser(Integer.valueOf(userDetailsImpl.getUserId()));
        model.addAttribute("lendingList", lendingViewList);

        int count = lendingService.countUser(Integer.valueOf(userDetailsImpl.getUserId()));
        model.addAttribute("lendingListCount", count);

        return "login/homeLayout";
    }
}
