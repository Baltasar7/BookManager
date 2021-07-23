package com.example.demo.login.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.login.domain.model.State;
import com.example.demo.login.domain.model.Stock;
import com.example.demo.login.domain.model.UserDetailsImpl;
import com.example.demo.login.domain.service.BookService;
import com.example.demo.login.domain.service.StockService;

@Controller
public class HomeStockController {
    @Autowired
    StockService stockService;
    @Autowired
    BookService bookService;

    @GetMapping("/stockList")
    public String getStockList(
        @AuthenticationPrincipal UserDetailsImpl userDetailsImpl,
        @PageableDefault(page = 0, size = 10) Pageable pageable,
        Model model) {
        model.addAttribute("userName", userDetailsImpl.getName());
        model.addAttribute("role", userDetailsImpl.getRole());
        model.addAttribute("contents", "login/stockList :: stockList_contents");

        int total = stockService.count();
        model.addAttribute("stockListCount", total);

        Page<Stock> stockPage = stockService.findPageByStock(pageable, total);
        List<Stock> list = stockPage.getContent();
        for(Stock stock: list) {
          stock.setState(State.getDispStr(stock.getState()));
        }
        model.addAttribute("page", stockPage);
        model.addAttribute("stockList", list);

        return "login/homeLayout";
    }

    @GetMapping("/stockList/pages")
    public String getStockListPage(
        @AuthenticationPrincipal UserDetailsImpl userDetailsImpl,
        @PageableDefault(page = 0, size = 10) Pageable pageable,
        Model model) {
        return getStockList(userDetailsImpl, pageable, model);
    }

/*
    @DeleteMapping(value = "/stockList", params = "delete")
    public String deleteStockDelete(
        @AuthenticationPrincipal UserDetailsImpl userDetailsImpl,
        HttpServletRequest req,
        Model model) {
        try {
          int stockId = Integer.parseInt(req.getParameter("delete"));
          boolean result = stockService.deleteOne(stockId);
          if (result == true) {
              model.addAttribute("result", "削除成功");
          } else {
              model.addAttribute("result", "削除失敗");
          }
        } catch(DataAccessException e) {
            model.addAttribute("result", "他テーブルとの参照性違反により、削除失敗");
        }
        return getStockList(userDetailsImpl, model);
    }
*/
    @PostMapping(value = "/stockList", params = "delete")
    public String postStockDelete(
        @AuthenticationPrincipal UserDetailsImpl userDetailsImpl,
        @PageableDefault(page = 0, size = 10) Pageable pageable,
        HttpServletRequest req,
        Model model) {
        try {
          int stockId = Integer.parseInt(req.getParameter("delete"));
          boolean result = stockService.deleteOne(stockId);
          if (result == true) {
              model.addAttribute("result", "削除成功");
          } else {
              model.addAttribute("result", "削除失敗");
          }
        } catch(DataAccessException e) {
            model.addAttribute("result", "他テーブルとの参照性違反により、削除失敗");
        }
        // TODO: 削除時のthymeleaf href urlがstockＬist固定なので先頭ページに戻る
        return getStockList(userDetailsImpl, pageable, model);
    }
}
