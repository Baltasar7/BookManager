package com.example.demo.login.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.login.domain.model.Book;
import com.example.demo.login.domain.model.BookRegistForm;
import com.example.demo.login.domain.model.GroupOrder;
import com.example.demo.login.domain.service.BookService;

@Controller
public class BookRegistController {
    @Autowired
    private BookService bookService;

    @GetMapping("/bookRegist")
    public String getBookRegist(@ModelAttribute BookRegistForm form, Model model) {
        model.addAttribute("contents", "login/bookRegist :: bookRegist_contents");
        return "login/homeLayout";
    }

    @PostMapping("/bookRegist")
    public String postBookRegist(
    		@ModelAttribute @Validated(GroupOrder.class) BookRegistForm form,
 	      BindingResult bindingResult,
	      Model model) {

        if (bindingResult.hasErrors()) {
            return getBookRegist(form, model);
       }

        // デバッグ
        System.out.println(form);

        Book book = new Book();
        book.setBookId(form.getBookId());
        book.setTitle(form.getTitle());
        book.setAuthor(form.getAuthor());
        book.setPublisher(form.getPublisher());

        boolean result = bookService.insert(book);

       if (result == true) {
            System.out.println("insert成功");
        } else {
            System.out.println("insert失敗");
        }
        return "redirect:/bookList";
    }
}