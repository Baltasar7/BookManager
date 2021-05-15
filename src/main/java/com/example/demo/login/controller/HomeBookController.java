package com.example.demo.login.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.login.domain.model.Book;
import com.example.demo.login.domain.model.BookRegistForm;
import com.example.demo.login.domain.model.UserDetailsImpl;
import com.example.demo.login.domain.service.BookService;

@Controller
public class HomeBookController {
    @Autowired
    BookService bookService;

    @GetMapping("/bookList")
    public String getBookList(
    		@AuthenticationPrincipal UserDetailsImpl userDetailsImpl,
    		Model model) {
        model.addAttribute("userName", userDetailsImpl.getName());
        model.addAttribute("role", userDetailsImpl.getRole());
        model.addAttribute("contents", "login/bookList :: bookList_contents");

        List<Book> bookList = bookService.selectAll();
        model.addAttribute("bookList", bookList);

        int count = bookService.count();
        model.addAttribute("bookListCount", count);

        return "login/homeLayout";
    }

    @GetMapping("/bookDetail/{id:.+}")
    public String getBookDetail(
    		@AuthenticationPrincipal UserDetailsImpl userDetailsImpl,
    		@ModelAttribute BookRegistForm form,
        Model model,
        @PathVariable("id") String bookId) {

        model.addAttribute("userName", userDetailsImpl.getName());
        model.addAttribute("role", userDetailsImpl.getRole());

        // Debug
	      System.out.println("bookId = " + bookId);
        model.addAttribute("contents", "login/bookDetail :: bookDetail_contents");

        if (bookId != null && bookId.length() > 0) {
            Book book = bookService.selectOne(Integer.parseInt(bookId));
            form.setBookId(book.getBookId().toString());
            form.setTitle(book.getTitle());
            form.setAuthor(book.getAuthor());
            form.setPublisher(book.getPublisher());
            model.addAttribute("BookRegistForm", form);
        }
        return "login/homeLayout";
    }

    @PostMapping(value = "/bookDetail", params = "update")
    public String postBookDetailUpdate(
    		@AuthenticationPrincipal UserDetailsImpl userDetailsImpl,
    		@ModelAttribute BookRegistForm form,
    		Model model) {
    		// Debug
    		System.out.println("更新ボタンの処理");

        Book book = new Book();
        book.setBookId(Integer.parseInt(form.getBookId()));
        book.setTitle(form.getTitle());
        book.setAuthor(form.getAuthor());
        book.setPublisher(form.getPublisher());

        try {
            boolean result = bookService.updateOne(book);
            if (result == true) {
                model.addAttribute("result", "更新成功");
            } else {
                model.addAttribute("result", "更新失敗");
            }
        } catch(DataAccessException e) {
            model.addAttribute("result", "更新失敗");
        }
        return getBookList(userDetailsImpl, model);
    }

    @PostMapping(value = "/bookDetail", params = "delete")
    public String postBookDetailDelete(
    		@AuthenticationPrincipal UserDetailsImpl userDetailsImpl,
    		@ModelAttribute BookRegistForm form, Model model) {
    		// Debug
        System.out.println("削除ボタンの処理");

        boolean result = bookService.deleteOne(Integer.parseInt(form.getBookId()));

        if (result == true) {
            model.addAttribute("result", "削除成功");
        } else {
            model.addAttribute("result", "削除失敗");
        }
        return getBookList(userDetailsImpl, model);
    }

    @GetMapping("/bookList/csv")
    public ResponseEntity<byte[]> getBookListCsv(Model model) {
        bookService.bookCsvOut();
        byte[] bytes = null;

        try {
            bytes = bookService.getFile("BookList.csv");
        } catch (IOException e) {
            e.printStackTrace();
        }

        HttpHeaders header = new HttpHeaders();
        header.add("Content-Type", "text/csv; charset=UTF-8");
        header.setContentDispositionFormData("filename", "BookList.csv");

        return new ResponseEntity<>(bytes, header, HttpStatus.OK);
    }
}
