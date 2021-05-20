package com.example.demo.login.controller;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.login.domain.model.GroupOrder;
import com.example.demo.login.domain.model.SignupForm;
import com.example.demo.login.domain.model.User;
import com.example.demo.login.domain.model.UserDetailsImpl;
import com.example.demo.login.domain.service.UserService;

@Controller
public class HomeUserController {
    @Autowired
    UserService userService;
	  private Map<Integer, String> departmentPulldown;

	  private Map<Integer, String> initDepartmentPulldown() {
	      Map<Integer, String> pulldown = new LinkedHashMap<>();
	      pulldown.put(0, "福岡営業所");
	      pulldown.put(1, "東京営業所");
	      pulldown.put(2, "大阪営業所");
	      pulldown.put(3, "名古屋営業所");
	      return pulldown;
	  }

    @GetMapping("/userList")
    public String getUserList(
    		@AuthenticationPrincipal UserDetailsImpl userDetailsImpl,
    		Model model) {
        model.addAttribute("userName", userDetailsImpl.getName());
        model.addAttribute("role", userDetailsImpl.getRole());
        model.addAttribute("contents", "login/userList :: userList_contents");

        List<User> userList = userService.selectAll();
        model.addAttribute("userList", userList);

        int count = userService.count();
        model.addAttribute("userListCount", count);

        return "login/homeLayout";
    }

    @GetMapping("/userDetail/{id:.+}")
    public String getUserDetail(
    		@AuthenticationPrincipal UserDetailsImpl userDetailsImpl,
    		@ModelAttribute SignupForm form,
        Model model, @PathVariable("id") String userId) {

    	  model.addAttribute("userName", userDetailsImpl.getName());
        model.addAttribute("role", userDetailsImpl.getRole());

        //System.out.println("userId = " + userId);
        model.addAttribute("contents", "login/userDetail :: userDetail_contents");

    		departmentPulldown = initDepartmentPulldown();
      	model.addAttribute("departmentPulldown", departmentPulldown);

        if (userId != null && userId.length() > 0) {
            User user = userService.selectOne(userId);

            form.setUserId(user.getUserId());
            form.setUserName(user.getUserName());
            form.setDepartment(user.getDepartment());
            model.addAttribute("signupForm", form);
        }
        return "login/homeLayout";
    }

    @PostMapping(value = "/userDetail", params = "update")
    public String postUserDetailUpdate(
    		@AuthenticationPrincipal UserDetailsImpl userDetailsImpl,
    		@ModelAttribute @Validated(GroupOrder.class) SignupForm form,
 	      BindingResult bindingResult,
    		Model model) {

	      if (bindingResult.hasErrors()) {
	        return getUserDetail(userDetailsImpl, form, model, form.getUserId());
	      }

        User user = new User();
        user.setUserId(form.getUserId());
        user.setPassword(form.getPassword());
        user.setUserName(form.getUserName());
        user.setDepartment(form.getDepartment());

        try {
        		boolean result = userService.updateOne(user);
			      if (result == true) {
                model.addAttribute("result", "更新成功");
			      } else {
			      		model.addAttribute("result", "更新失敗");
				        return getUserDetail(userDetailsImpl, form, model, form.getUserId());
			      }
        } catch(DataAccessException e) {
        		model.addAttribute("result", "更新失敗");
  	        return getUserDetail(userDetailsImpl, form, model, form.getUserId());
        }
        return getUserList(userDetailsImpl, model);
    }

    @PostMapping(value = "/userDetail", params = "delete")
    public String postUserDetailDelete(
    		@AuthenticationPrincipal UserDetailsImpl userDetailsImpl,
    		@ModelAttribute SignupForm form,
 	      BindingResult bindingResult,
    		Model model) {

        try {
            boolean result = userService.deleteOne(form.getUserId());
				    if (result == true) {
	              model.addAttribute("result", "削除成功");
				    } else {
	              model.addAttribute("result", "削除失敗");
	    	        return getUserDetail(userDetailsImpl, form, model, form.getUserId());
				    }
        } catch(DataAccessException e) {
            model.addAttribute("result", "他テーブルとの参照性違反により、削除失敗");
  	        return getUserDetail(userDetailsImpl, form, model, form.getUserId());
        }
        return getUserList(userDetailsImpl, model);
    }

    @GetMapping("/signup")
    public String getSignUp(
    		@AuthenticationPrincipal UserDetailsImpl userDetailsImpl,
    		@ModelAttribute SignupForm form,
    		Model model) {
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

        User user = new User();
        user.setUserId(form.getUserId());
        user.setPassword(form.getPassword());
        user.setUserName(form.getUserName());
        user.setDepartment(form.getDepartment());
        user.setRole("ROLE_GENERAL");

        try {
            boolean result = userService.insert(user);
				    if (result == true) {
	              model.addAttribute("result", "追加成功");
		            return getUserList(userDetailsImpl, model);
				    } else {
	              model.addAttribute("result", "追加失敗");
		            return getSignUp(userDetailsImpl, form, model);
				    }
        } catch(DataAccessException e) {
            model.addAttribute("result", "追加失敗");
            return getSignUp(userDetailsImpl, form, model);
        }
    }

/*
    @GetMapping("/userList/csv")
    public ResponseEntity<byte[]> getUserListCsv(Model model) {
        userService.userCsvOut();
        byte[] bytes = null;

        try {
            bytes = userService.getFile("UserList.csv");
        } catch (IOException e) {
            e.printStackTrace();
        }

        HttpHeaders header = new HttpHeaders();
        header.add("Content-Type", "text/csv; charset=UTF-8");
        header.setContentDispositionFormData("filename", "UserList.csv");

        return new ResponseEntity<>(bytes, header, HttpStatus.OK);
    }
*/
}
