package com.example.demo.login.controller;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.login.domain.model.SignupForm;
import com.example.demo.login.domain.model.User;
import com.example.demo.login.domain.service.UserService;

@Controller
public class HomeController {

    @Autowired
    UserService userService;

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
     * ホーム画面のGET用メソッド
     */
    @GetMapping("/home")
    public String getHome(Model model) {

        //コンテンツ部分にユーザー詳細を表示するための文字列を登録
        model.addAttribute("contents", "login/home :: home_contents");

        return "login/homeLayout";
    }

    /**
     * ユーザー一覧画面のGETメソッド用処理.
     */
    @GetMapping("/userList")
    public String getUserList(Model model) {
        model.addAttribute("contents", "login/userList :: userList_contents");

        List<User> userList = userService.selectMany();
        model.addAttribute("userList", userList);

        int count = userService.count();
        model.addAttribute("userListCount", count);

        return "login/homeLayout";
    }

    /**
     * ユーザー詳細画面のGETメソッド用処理.
     */
    @GetMapping("/userDetail/{id:.+}")
    public String getUserDetail(@ModelAttribute SignupForm form,
        Model model, @PathVariable("id") String userId) {
	  		// デバッグ
	      System.out.println("userId = " + userId);
        model.addAttribute("contents", "login/userDetail :: userDetail_contents");

    		departmentPulldown = initDepartmentPulldown();
      	model.addAttribute("departmentPulldown", departmentPulldown);

        if (userId != null && userId.length() > 0) {
            User user = userService.selectOne(userId);

            form.setUserId(user.getUserId()); //ユーザーID
            form.setUserName(user.getUserName()); //ユーザー名
            form.setDepartment(user.getDepartment()); //所属部署
            model.addAttribute("signupForm", form);
        }

        return "login/homeLayout";
    }

    /**
     * ユーザー更新用処理.
     */
    @PostMapping(value = "/userDetail", params = "update")
    public String postUserDetailUpdate(@ModelAttribute SignupForm form, Model model) {
    		// デバッグ
    		System.out.println("更新ボタンの処理");

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
            }
        } catch(DataAccessException e) {
            model.addAttribute("result", "更新失敗");
        }
        return getUserList(model);
    }

    /**
     * ユーザー削除用処理.
     */
    @PostMapping(value = "/userDetail", params = "delete")
    public String postUserDetailDelete(@ModelAttribute SignupForm form, Model model) {
    		// デバッグ
        System.out.println("削除ボタンの処理");

        boolean result = userService.deleteOne(form.getUserId());

        if (result == true) {
            model.addAttribute("result", "削除成功");
        } else {
            model.addAttribute("result", "削除失敗");
        }
        return getUserList(model);
    }

    /**
     * ログアウト用処理.
     */
    @PostMapping("/logout")
    public String postLogout() {
        return "redirect:/login";
    }

    /**
     * ユーザー一覧のCSV出力用処理.
     */
    @GetMapping("/userList/csv")
    public ResponseEntity<byte[]> getUserListCsv(Model model) {
        userService.userCsvOut();
        byte[] bytes = null;

        try {
            bytes = userService.getFile("sample.csv");
        } catch (IOException e) {
            e.printStackTrace();
        }

        HttpHeaders header = new HttpHeaders();
        header.add("Content-Type", "text/csv; charset=UTF-8");
        header.setContentDispositionFormData("filename", "sample.csv");

        return new ResponseEntity<>(bytes, header, HttpStatus.OK);
    }

    /**
     * アドミン権限専用画面のGET用メソッド.
     */
    @GetMapping("/admin")
    public String getAdmin(Model model) {
        model.addAttribute("contents", "login/admin :: admin_contents");
        return "login/homeLayout";
    }
}
