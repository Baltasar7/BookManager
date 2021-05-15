package com.example.demo.login.controller;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.login.domain.model.GroupOrder;
import com.example.demo.login.domain.model.SignupForm;
import com.example.demo.login.domain.model.User;
import com.example.demo.login.domain.model.UserDetailsImpl;
import com.example.demo.login.domain.service.UserService;

@Controller
public class SignupController {
    @Autowired
    private UserService userService;
		private Map<Integer, String> departmentPulldown;

		private Map<Integer, String> initDepartmentPulldown() {
		    Map<Integer, String> pulldown = new LinkedHashMap<>();

		    pulldown.put(0, "福岡営業所");
		    pulldown.put(1, "東京営業所");
		    pulldown.put(2, "大阪営業所");
		    pulldown.put(3, "名古屋営業所");

		    return pulldown;
		}

    @GetMapping("/signup")
    public String getSignUp(
    		@AuthenticationPrincipal UserDetailsImpl userDetailsImpl,
    		@ModelAttribute SignupForm form, Model model) {
        model.addAttribute("userName", userDetailsImpl.getName());
        model.addAttribute("role", userDetailsImpl.getRole());
        model.addAttribute("contents", "login/signup :: signup_contents");

      	departmentPulldown = initDepartmentPulldown();
      	model.addAttribute("departmentPulldown", departmentPulldown);

        return "login/homeLayout";
    }

    @PostMapping("/signup")
    public String postSignUp(
    		@AuthenticationPrincipal UserDetailsImpl userDetailsImpl,
    		@ModelAttribute @Validated(GroupOrder.class) SignupForm form,
 	      BindingResult bindingResult,
	      Model model) {

        if (bindingResult.hasErrors()) {
            return getSignUp(userDetailsImpl, form, model);
       }

        // デバッグ
        System.out.println(form);

        User user = new User();
        user.setUserId(form.getUserId());
        user.setPassword(form.getPassword());
        user.setUserName(form.getUserName());
        user.setDepartment(form.getDepartment());
        user.setRole("ROLE_GENERAL");

        boolean result = userService.insert(user);

       if (result == true) {
            System.out.println("insert成功");
        } else {
            System.out.println("insert失敗");
        }
        return "redirect:/userList";
    }
}