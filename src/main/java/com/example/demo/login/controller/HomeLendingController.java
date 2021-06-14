package com.example.demo.login.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

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
import com.example.demo.login.domain.model.Lending;
import com.example.demo.login.domain.model.LendingRegistForm;
import com.example.demo.login.domain.model.LendingView;
import com.example.demo.login.domain.model.State;
import com.example.demo.login.domain.model.UserDetailsImpl;
import com.example.demo.login.domain.service.BookService;
//import com.example.demo.login.domain.model.LendingRegistForm;
import com.example.demo.login.domain.service.LendingService;
import com.example.demo.login.domain.service.StockService;

@Controller
public class HomeLendingController {
    @Autowired
    LendingService lendingService;
    @Autowired
    BookService bookService;
    @Autowired
    StockService stockService;

    @GetMapping("/lendingList")
    public String getLendingList(
    		@AuthenticationPrincipal UserDetailsImpl userDetailsImpl,
    		Model model) {
        model.addAttribute("userName", userDetailsImpl.getName());
        model.addAttribute("role", userDetailsImpl.getRole());
        model.addAttribute("contents", "login/lendingList :: lendingList_contents");

        List<LendingView> lendingViewList = lendingService.selectAll();
        lendingService.setLimitDate(lendingViewList);
        for(LendingView lendingView: lendingViewList) {
        	lendingView.setState(State.getDispStr(lendingView.getState()));
        }
        model.addAttribute("lendingList", lendingViewList);

        int count = lendingService.countAll();
        model.addAttribute("lendingListCount", count);

        return "login/homeLayout";
    }

    @PostMapping(value = "/lendingList", params = "apply")
    public String postApplyLendingList(
    		@AuthenticationPrincipal UserDetailsImpl userDetailsImpl,
        HttpServletRequest req,
    		Model model) {
	      try {
	      	int lendingId = Integer.parseInt(req.getParameter("apply"));
	      	int stockId = lendingService.getStockId(lendingId);
	        if (stockService.applyLending(stockId)) {
	            lendingService.setLendingDate(lendingId);
	            model.addAttribute("result", "承認成功");
	        } else {
	            model.addAttribute("result", "承認失敗、現在の状態は承認可能ではありません。");
	        }
	      } catch(DataAccessException e) {
	          model.addAttribute("result", "承認失敗");
	      }
	      return getLendingList(userDetailsImpl, model);
    }

    @PostMapping(value = "/lendingList", params = "delete")
    public String postDeleteLendingList(
    		@AuthenticationPrincipal UserDetailsImpl userDetailsImpl,
        HttpServletRequest req,
    		Model model) {
	      try {
	      	int lendingId = Integer.parseInt(req.getParameter("delete"));
	        if (lendingService.deleteLending(lendingId)) {
	            model.addAttribute("result", "削除成功");
	        } else {
	            model.addAttribute("result", "削除失敗、現在の状態は削除可能ではありません。");
	        }
	      } catch(DataAccessException e) {
	          model.addAttribute("result", "削除失敗");
	      }
	      return getLendingList(userDetailsImpl, model);
    }

    @GetMapping("/lendingEdit/{id:.+}")
	  public String getLendingEdit(
    		@AuthenticationPrincipal UserDetailsImpl userDetailsImpl,
	  		@ModelAttribute LendingRegistForm form,
	      Model model,
	      @PathVariable("id") String lendingId) {

	      model.addAttribute("userName", userDetailsImpl.getName());
	      model.addAttribute("role", userDetailsImpl.getRole());
	      model.addAttribute("contents", "login/lendingEdit :: lendingEdit_contents");

	      if (lendingId != null && lendingId.length() > 0) {
	          Lending lending = lendingService.selectOne(Integer.parseInt(lendingId));
	          form.setLendingId(lending.getLendingId().toString());
	          form.setStockId(lending.getStockId().toString());
	          form.setUserId(lending.getUserId());
	          model.addAttribute("LendingRegistForm", form);
	      }
	      return "login/homeLayout";
	  }

	  @PostMapping(value = "/lendingEdit", params = "update")
	  public String postLendingEditUpdate(
    		@AuthenticationPrincipal UserDetailsImpl userDetailsImpl,
	  		@ModelAttribute @Validated(GroupOrder.class) LendingRegistForm form,
 	      BindingResult bindingResult,
	  		Model model) {

	      if (bindingResult.hasErrors()) {
	        return getLendingEdit(userDetailsImpl, form, model, form.getLendingId());
	      }

//	      model.addAttribute("userName", userDetailsImpl.getName());
//	      model.addAttribute("role", userDetailsImpl.getRole());

	      Lending lending = new Lending();
	      lending.setLendingId(Integer.parseInt(form.getLendingId()));
	      lending.setStockId(Integer.parseInt(form.getStockId()));
	      lending.setUserId(form.getUserId());

	      try {
	          boolean result = lendingService.updateOne(lending);
	          if (result == true) {
	              model.addAttribute("result", "更新成功");
	          } else {
	              model.addAttribute("result", "更新失敗");
	    	        return getLendingEdit(userDetailsImpl, form, model, form.getLendingId());
	          }
	      } catch(DataAccessException e) {
	          model.addAttribute("result", "他テーブルとの参照性違反により、削除失敗");
		        return getLendingEdit(userDetailsImpl, form, model, form.getLendingId());
	      }
	      return getLendingList(userDetailsImpl, model);
	  }

	  @PostMapping(value = "/lendingEdit", params = "delete")
	  public String postLendingEditDelete(
    		@AuthenticationPrincipal UserDetailsImpl userDetailsImpl,
	  		@ModelAttribute LendingRegistForm form,
 	      BindingResult bindingResult,
	  		Model model) {

//	      model.addAttribute("userName", userDetailsImpl.getName());
//	      model.addAttribute("role", userDetailsImpl.getRole());

	      try {
		      boolean result = lendingService.deleteOne(Integer.parseInt(form.getLendingId()));
		      if (result == true) {
              model.addAttribute("result", "削除成功");
		      } else {
              model.addAttribute("result", "削除失敗");
    	        return getLendingEdit(userDetailsImpl, form, model, form.getLendingId());
		      }
	      } catch(DataAccessException e) {
		        model.addAttribute("result", "他テーブルとの参照性違反により、削除失敗");
		        return getLendingEdit(userDetailsImpl, form, model, form.getLendingId());
	      }
	      return getLendingList(userDetailsImpl, model);
	  }

    @GetMapping("/lendingRegist")
    public String getLendingRegist(
    		@AuthenticationPrincipal UserDetailsImpl userDetailsImpl,
    		@ModelAttribute LendingRegistForm form,
    		Model model) {
        model.addAttribute("userName", userDetailsImpl.getName());
        model.addAttribute("role", userDetailsImpl.getRole());
        model.addAttribute("contents", "login/lendingRegist :: lendingRegist_contents");
        return "login/homeLayout";
    }

    @PostMapping("/lendingRegist")
    public String postLendingRegist(
    		@AuthenticationPrincipal UserDetailsImpl userDetailsImpl,
    		@ModelAttribute @Validated(GroupOrder.class) LendingRegistForm form,
 	      BindingResult bindingResult,
	      Model model) {

        if (bindingResult.hasErrors()) {
            return getLendingRegist(userDetailsImpl, form, model);
       }

        Lending lending = new Lending();
        lending.setStockId(Integer.parseInt(form.getStockId()));
        lending.setUserId(form.getUserId());

        try {
        	  if (!stockService.isStock(lending.getStockId())) {
              model.addAttribute("result", "追加失敗、指定した在庫は貸出可能状態ではありません。");
	            return getLendingRegist(userDetailsImpl, form, model);
        	  }

        	  boolean result = lendingService.insert(lending);
				    if (result) {
				    		stockService.updateLending(lending.getStockId());
	              lendingService.setLendingDate(lending.getLendingId());
	              model.addAttribute("result", "追加成功");
		            return getLendingList(userDetailsImpl, model);
				    } else {
	              model.addAttribute("result", "追加失敗");
		            return getLendingRegist(userDetailsImpl, form, model);
				    }
        } catch(DataAccessException e) {
            model.addAttribute("result", "指定したIDが存在しない為、追加失敗");
            return getLendingRegist(userDetailsImpl, form, model);
        }
    }

/*
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
*/
}
