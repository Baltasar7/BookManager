package com.example.demo.login.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.login.domain.model.LendingView;
import com.example.demo.login.domain.model.UserDetailsImpl;
//import com.example.demo.login.domain.model.LendingRegistForm;
import com.example.demo.login.domain.service.LendingService;
import com.example.demo.login.domain.service.StockService;

@Controller
public class HomeLendingStatusController {
    @Autowired
    LendingService lendingService;
    @Autowired
    StockService stockService;

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

    @PostMapping(value = "/lendingStatus", params = "reset")
    public String postLendingList(
    		@AuthenticationPrincipal UserDetailsImpl userDetailsImpl,
        HttpServletRequest req,
    		Model model) {
	      try {
	      	int lendingId = Integer.parseInt(req.getParameter("reset"));
	        if (lendingService.resetApply(lendingId)) {
	            model.addAttribute("result", "申請取消成功");
	        } else {
	            model.addAttribute("result", "申請取消失敗、現在の状態は申請取消可能ではありません。");
	        }
	      } catch(DataAccessException e) {
	          model.addAttribute("result", "申請取消失敗");
	      }
	      return getLendingList(userDetailsImpl, model);
    }

}
