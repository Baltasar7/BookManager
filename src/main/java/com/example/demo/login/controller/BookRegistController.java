package com.example.demo.login.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
import com.example.demo.login.domain.model.UserDetailsImpl;
import com.example.demo.login.domain.service.BookService;

@Controller
public class BookRegistController {
    @Autowired
    private BookService bookService;

    @GetMapping("/bookRegist")
    public String getBookRegist(
    		@AuthenticationPrincipal UserDetailsImpl userDetailsImpl,
    		@ModelAttribute BookRegistForm form, Model model) {
        model.addAttribute("userName", userDetailsImpl.getName());
        model.addAttribute("role", userDetailsImpl.getRole());
        model.addAttribute("contents", "login/bookRegist :: bookRegist_contents");
        return "login/homeLayout";
    }

    @PostMapping("/bookRegist")
    public String postBookRegist(
    		@AuthenticationPrincipal UserDetailsImpl userDetailsImpl,
    		@ModelAttribute @Validated(GroupOrder.class) BookRegistForm form,
 	      BindingResult bindingResult,
	      Model model) {

        if (bindingResult.hasErrors()) {
            System.out.println("bindingResult hasErrors() true");
            return getBookRegist(userDetailsImpl, form, model);
       }

        // Debug
        System.out.println(form);

        Book book = new Book();
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