package com.example.demo.login.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.demo.login.domain.model.LendingView;
import com.example.demo.login.domain.model.UserDetailsImpl;
//import com.example.demo.login.domain.model.LendingRegistForm;
import com.example.demo.login.domain.service.LendingService;

@Controller
public class HomeLendingController {
    @Autowired
    LendingService lendingService;

    @GetMapping("/lendingList")
    public String getLendingList(
    		@AuthenticationPrincipal UserDetailsImpl userDetailsImpl,
    		Model model) {
  	 		// Debug
  	 		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
  	 		UserDetailsImpl userDatails = UserDetailsImpl.class.cast(auth.getPrincipal());

        model.addAttribute("userName", userDetailsImpl.getName());
        model.addAttribute("role", userDetailsImpl.getRole());
        model.addAttribute("contents", "login/lendingList :: lendingList_contents");

        List<LendingView> lendingViewList = lendingService.selectAll();
        model.addAttribute("lendingList", lendingViewList);

        int count = lendingService.countAll();
        model.addAttribute("lendingListCount", count);

        return "login/homeLayout";
    }

	/*    @GetMapping("/lendingDetail/{id:.+}")
	  public String getLendingDetail(
	  		@ModelAttribute LendingRegistForm form,
	      Model model,
	      @PathVariable("id") String lendingId) {

	  	  // Debug
		      System.out.println("lendingId = " + lendingId);
	      model.addAttribute("contents", "login/lendingDetail :: lendingDetail_contents");

	      if (lendingId != null && lendingId.length() > 0) {
	          Lending lending = lendingService.selectOne(Integer.parseInt(lendingId));
	          form.setLendingId(lending.getLendingId().toString());
	          form.setTitle(lending.getTitle());
	          form.setAuthor(lending.getAuthor());
	          form.setPublisher(lending.getPublisher());
	          model.addAttribute("LendingRegistForm", form);
	      }
	      return "login/homeLayout";
	  }

	  @PostMapping(value = "/lendingDetail", params = "update")
	  public String postLendingDetailUpdate(@ModelAttribute LendingRegistForm form, Model model) {
	  		// Debug
	  		System.out.println("更新ボタンの処理");

	      Lending lending = new Lending();
	      lending.setLendingId(Integer.parseInt(form.getLendingId()));
	      lending.setTitle(form.getTitle());
	      lending.setAuthor(form.getAuthor());
	      lending.setPublisher(form.getPublisher());

	      try {
	          boolean result = lendingService.updateOne(lending);
	          if (result == true) {
	              model.addAttribute("result", "更新成功");
	          } else {
	              model.addAttribute("result", "更新失敗");
	          }
	      } catch(DataAccessException e) {
	          model.addAttribute("result", "更新失敗");
	      }
	      return getLendingList(model);
	  }

	  @PostMapping(value = "/lendingDetail", params = "delete")
	  public String postLendingDetailDelete(@ModelAttribute LendingRegistForm form, Model model) {
	  		// Debug
	      System.out.println("削除ボタンの処理");

	      boolean result = lendingService.deleteOne(Integer.parseInt(form.getLendingId()));

	      if (result == true) {
	          model.addAttribute("result", "削除成功");
	      } else {
	          model.addAttribute("result", "削除失敗");
	      }
	      return getLendingList(model);
	  }
	*/
    @GetMapping("/lendingList/csv")
    public ResponseEntity<byte[]> getLendingListCsv(Model model) {
        lendingService.lendingCsvOut();
        byte[] bytes = null;

        try {
            bytes = lendingService.getFile("LendingList.csv");
        } catch (IOException e) {
            e.printStackTrace();
        }

        HttpHeaders header = new HttpHeaders();
        header.add("Content-Type", "text/csv; charset=UTF-8");
        header.setContentDispositionFormData("filename", "LendingList.csv");

        return new ResponseEntity<>(bytes, header, HttpStatus.OK);
    }
}
