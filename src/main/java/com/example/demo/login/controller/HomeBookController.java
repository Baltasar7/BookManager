package com.example.demo.login.controller;

import java.util.List;

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

import com.example.demo.login.domain.model.Book;
import com.example.demo.login.domain.model.BookRegistForm;
import com.example.demo.login.domain.model.GroupOrder;
import com.example.demo.login.domain.model.UserDetailsImpl;
import com.example.demo.login.domain.service.BookService;
import com.example.demo.login.domain.service.StockService;

@Controller
public class HomeBookController {
    @Autowired
    BookService bookService;
    @Autowired
    StockService stockService;

    @GetMapping("/bookList")
    public String getBookList(
    		@AuthenticationPrincipal UserDetailsImpl userDetailsImpl,
    		Model model) {
        model.addAttribute("userName", userDetailsImpl.getName());
        model.addAttribute("role", userDetailsImpl.getRole());
        model.addAttribute("contents", "login/bookList :: bookList_contents");

        List<Book> bookList = bookService.selectAll();
        for(Book book: bookList) {
        	book.setStock(stockService.getStockCount(book.getBookId()));
        	book.setRest(stockService.getRestCount(book.getBookId()));
        }

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
        model.addAttribute("contents", "login/bookDetail :: bookDetail_contents");

        if (bookId != null && bookId.length() > 0) {
            Book book = bookService.selectOne(Integer.parseInt(bookId));
            form.setBookId(book.getBookId().toString());
            form.setTitle(book.getTitle());
            form.setAuthor(book.getAuthor());
            form.setPublisher(book.getPublisher());
            form.setStock("2");
            form.setRest("1");
//            form.setStock(book.getStock().toString());
//            form.setRest(book.getRest().toString());
            model.addAttribute("BookRegistForm", form);
        }
        return "login/homeLayout";
    }

    @PostMapping(value = "/bookDetail", params = "update")
    public String postBookDetailUpdate(
    		@AuthenticationPrincipal UserDetailsImpl userDetailsImpl,
    		@ModelAttribute @Validated(GroupOrder.class) BookRegistForm form,
 	      BindingResult bindingResult,
    		Model model) {

	      if (bindingResult.hasErrors()) {
	        return getBookDetail(userDetailsImpl, form, model, form.getBookId());
	      }

        Book book = new Book();
        book.setBookId(Integer.parseInt(form.getBookId()));
        book.setTitle(form.getTitle());
        book.setAuthor(form.getAuthor());
        book.setPublisher(form.getPublisher());
//        book.setStock(Integer.parseInt(form.getStock()));
//        book.setRest(Integer.parseInt(form.getRest()));

        try {
            boolean result = bookService.updateOne(book);
		        if (result == true) {
                model.addAttribute("result", "更新成功");
		        } else {
                model.addAttribute("result", "更新失敗");
      	        return getBookDetail(userDetailsImpl, form, model, form.getBookId());
		        }
        } catch(DataAccessException e) {
	          model.addAttribute("result", "更新失敗");
		        return getBookDetail(userDetailsImpl, form, model, form.getBookId());
        }
        return getBookList(userDetailsImpl, model);
    }

    @PostMapping(value = "/bookDetail", params = "delete")
    public String postBookDetailDelete(
    		@AuthenticationPrincipal UserDetailsImpl userDetailsImpl,
    		@ModelAttribute BookRegistForm form,
 	      BindingResult bindingResult,
 	      Model model) {

	      try {
            boolean result = bookService.deleteOne(Integer.parseInt(form.getBookId()));
			      if (result == true) {
                model.addAttribute("result", "削除成功");
			      } else {
                model.addAttribute("result", "削除失敗");
      	        return getBookDetail(userDetailsImpl, form, model, form.getBookId());
			      }
        } catch(DataAccessException e) {
		        model.addAttribute("result", "他テーブルとの参照性違反により、削除失敗");
		        return getBookDetail(userDetailsImpl, form, model, form.getBookId());
        }
        return getBookList(userDetailsImpl, model);
    }

    @GetMapping("/bookRegist")
    public String getBookRegist(
    		@AuthenticationPrincipal UserDetailsImpl userDetailsImpl,
    		@ModelAttribute BookRegistForm form,
    		Model model) {
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

        Book book = new Book();
        book.setTitle(form.getTitle());
        book.setAuthor(form.getAuthor());
        book.setPublisher(form.getPublisher());
//        book.setStock(Integer.parseInt(form.getStock()));
//        book.setRest(Integer.parseInt(form.getRest()));

        try {
            boolean result = bookService.insert(book);
						if (result == true) {
						    model.addAttribute("result", "追加成功");
						    return getBookList(userDetailsImpl, model);
						} else {
						    model.addAttribute("result", "追加失敗");
						    return getBookRegist(userDetailsImpl, form, model);
						}
        } catch(DataAccessException e) {
            model.addAttribute("result", "追加失敗");
            return getBookRegist(userDetailsImpl, form, model);
        }
    }

/*
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
*/
}
