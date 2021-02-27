package com.example.demo.login.controller;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.example.demo.login.domain.service.UserService;

@Controller
public class SignupController {

    @Autowired
    private UserService userService;

	  // プルダウンメニュー用変数
	  private Map<Integer, String> departmentPulldown;

	  /**
	   * ラジオボタンの初期化メソッド.
	   */
	  private Map<Integer, String> initDepartmentPulldown() {
	      Map<Integer, String> pulldown = new LinkedHashMap<>();

	      pulldown.put(0, "福岡営業所");
	      pulldown.put(1, "東京営業所");
	      pulldown.put(2, "大阪営業所");
	      pulldown.put(3, "名古屋営業所");

	      return pulldown;
	  }

    /**
     * ユーザー登録画面のGETメソッド用処理.
     */
    @GetMapping("/signup")
    public String getSignUp(@ModelAttribute SignupForm form, Model model) {
      	departmentPulldown = initDepartmentPulldown();
      	model.addAttribute("departmentPulldown", departmentPulldown);

        return "login/signup";
    }

    /**
     * ユーザー登録画面のPOSTメソッド用処理.
     */
    @PostMapping("/signup")
    public String postSignUp(@ModelAttribute @Validated(GroupOrder.class) SignupForm form,
 	      BindingResult bindingResult,
	      Model model) {

        if (bindingResult.hasErrors()) {
            return getSignUp(form, model);
       }

        // デバッグ
        System.out.println(form);

        User user = new User();
        user.setUserId(form.getUserId()); //ユーザーID
        user.setPassword(form.getPassword()); //パスワード
        user.setUserName(form.getUserName()); //ユーザー名
        user.setDepartment(form.getDepartment()); //所属部署
        user.setRole("ROLE_GENERAL"); //ロール（一般）

        boolean result = userService.insert(user);

       if (result == true) {
            System.out.println("insert成功");
        } else {
            System.out.println("insert失敗");
        }
        return "redirect:/login";
    }
}