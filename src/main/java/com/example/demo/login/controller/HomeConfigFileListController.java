package com.example.demo.login.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.login.domain.model.Book;
import com.example.demo.login.domain.model.Lending;
import com.example.demo.login.domain.model.Stock;
import com.example.demo.login.domain.model.User;
import com.example.demo.login.domain.model.UserDetailsImpl;
import com.example.demo.login.domain.service.BookService;
import com.example.demo.login.domain.service.LendingService;
import com.example.demo.login.domain.service.StockService;
import com.example.demo.login.domain.service.UserService;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;

@Controller
public class HomeConfigFileListController {
    @Autowired
    BookService bookService;
    @Autowired
    StockService stockService;
    @Autowired
    UserService userService;
    @Autowired
    LendingService lendingService;

    @GetMapping("/configFileList")
    public String getUserList(
        @AuthenticationPrincipal UserDetailsImpl userDetailsImpl,
        Model model) {
        model.addAttribute("userName", userDetailsImpl.getName());
        model.addAttribute("role", userDetailsImpl.getRole());
        model.addAttribute("contents", "login/configFileList :: configFileList_contents");

        return "login/homeLayout";
    }

    @PostMapping(value = "/configFileList", params = "user_upload")
    public String postUserFileUpload(
        @AuthenticationPrincipal UserDetailsImpl userDetailsImpl,
        @RequestParam("user_file") MultipartFile file,
        Model model) {
        try (Reader reader = new BufferedReader(new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8))) {
          CsvToBean<User> csvToUser = new CsvToBeanBuilder<User>(reader).withType(User.class).build();
          List<User> userList = csvToUser.parse();
          userService.insertListFromFile(userList, userDetailsImpl.getUserId());
          model.addAttribute("result", "ユーザ登録成功");
        }
        catch (IOException e) {
          System.err.println(e.toString());
          model.addAttribute("result", "ユーザ登録失敗。ファイルの内容を再確認してください。");
        }
        catch (Exception e) {
          System.err.println(e.toString());
          model.addAttribute("result", "ユーザ登録失敗。想定外のエラーが発生しました。");
        }
        return getUserList(userDetailsImpl, model);
    }

    @PostMapping(value = "/configFileList", params = "book_upload")
    public String postBookFileUpload(
        @AuthenticationPrincipal UserDetailsImpl userDetailsImpl,
        @RequestParam("book_file") MultipartFile file,
        Model model) {
        try (Reader reader = new BufferedReader(new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8))) {
          CsvToBean<Book> csvToUser = new CsvToBeanBuilder<Book>(reader).withType(Book.class).build();
          List<Book> bookList = csvToUser.parse();
          bookService.insertListFromFile(bookList);
          model.addAttribute("result", "書籍登録成功");
        }
        catch (IOException e) {
          System.err.println(e.toString());
          model.addAttribute("result", "書籍登録失敗。ファイルの内容を再確認してください。");
        }
        catch (Exception e) {
          System.err.println(e.toString());
          model.addAttribute("result", "書籍登録失敗。想定外のエラーが発生しました。");
        }
        return getUserList(userDetailsImpl, model);
    }

    @PostMapping(value = "/configFileList", params = "stock_upload")
    public String postStockFileUpload(
        @AuthenticationPrincipal UserDetailsImpl userDetailsImpl,
        @RequestParam("stock_file") MultipartFile file,
        Model model) {
        try (Reader reader = new BufferedReader(new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8))) {
          CsvToBean<Stock> csvToUser = new CsvToBeanBuilder<Stock>(reader).withType(Stock.class).build();
          List<Stock> stockList = csvToUser.parse();
          stockService.insertListFromFile(stockList);
          model.addAttribute("result", "在庫登録成功");
        }
        catch (IOException e) {
          System.err.println(e.toString());
          model.addAttribute("result", "在庫登録失敗。ファイルの内容を再確認してください。");
        }
        catch (Exception e) {
          System.err.println(e.toString());
          model.addAttribute("result", "在庫登録失敗。想定外のエラーが発生しました。");
        }
        return getUserList(userDetailsImpl, model);
    }

    @PostMapping(value = "/configFileList", params = "lending_upload")
    public String postLendingFileUpload(
        @AuthenticationPrincipal UserDetailsImpl userDetailsImpl,
        @RequestParam("lending_file") MultipartFile file,
        Model model) {
        try (Reader reader = new BufferedReader(new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8))) {
          CsvToBean<Lending> csvToUser = new CsvToBeanBuilder<Lending>(reader).withType(Lending.class).build();
          List<Lending> lendingList = csvToUser.parse();
          lendingService.insertListFromFile(lendingList);
          model.addAttribute("result", "貸出登録成功");
        }
        catch (IOException e) {
          System.err.println(e.toString());
          model.addAttribute("result", "貸出登録失敗。ファイルの内容を再確認してください。");
        }
        catch (Exception e) {
          System.err.println(e.toString());
          model.addAttribute("result", "貸出登録失敗。想定外のエラーが発生しました。");
        }
        return getUserList(userDetailsImpl, model);
    }

    @PostMapping(value = "/configFileList", params = "bookimg_upload")
    public String postBookImgFileUpload(
        @AuthenticationPrincipal UserDetailsImpl userDetailsImpl,
        @RequestParam("bookimg_file") MultipartFile file,
        Model model) {
		/*        try (Reader reader = new BufferedReader(new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8))) {
		      CsvToBean<Book> csvToUser = new CsvToBeanBuilder<Book>(reader).withType(Book.class).build();
		      List<Book> bookList = csvToUser.parse();
		      bookService.insertListFromFile(bookList);
		      model.addAttribute("result", "書籍登録成功");
		    }
		    catch (IOException e) {
		      System.err.println(e.toString());
		      model.addAttribute("result", "書籍登録失敗。ファイルの内容を再確認してください。");
		    }
		    catch (Exception e) {
		      System.err.println(e.toString());
		      model.addAttribute("result", "書籍登録失敗。想定外のエラーが発生しました。");
		    }
		*/
	    return getUserList(userDetailsImpl, model);
    }

    @PostMapping(value = "/configFileList", params = "all_download")
    public String postAllFileDownload(
        @AuthenticationPrincipal UserDetailsImpl userDetailsImpl,
        Model model) {
        if (downloadDbTableFiles()) {
          model.addAttribute("result", "ダウンロード成功。ユーザのパスワード情報は暗号化されています。");
        }
        else {
          model.addAttribute("result", "ダウンロード失敗");
        }
        return getUserList(userDetailsImpl, model);
    }

    @PostMapping(value = "/configFileList", params = "all_delete")
    public String postAllDelete(
        @AuthenticationPrincipal UserDetailsImpl userDetailsImpl,
        Model model) {
        if (!downloadDbTableFiles()) {
          model.addAttribute("result", "現在のデータベース情報の保存に失敗した為、全削除をキャンセルしました。");
        }
        else {
          try {
            lendingService.deleteAll();
            stockService.deleteAll();
            bookService.deleteAll();
            userService.deleteAllWithoutCurrentUser(userDetailsImpl.getUserId());
            model.addAttribute("result", "全削除成功。削除前のデータベース情報をダウンロードしました。ユーザのパスワード情報は暗号化されています。");
          }
          catch (Exception e) {
            System.err.println(e.toString());
            model.addAttribute("result", "全削除失敗。削除試行時のデータベース情報をダウンロードしました。ユーザのパスワード情報は暗号化されています。");
          }
        }
        return getUserList(userDetailsImpl, model);
    }

    private boolean downloadDbTableFiles() {
      String dateTimeStr = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd-hhmmss"));
      try (Writer bookWriter = Files.newBufferedWriter(Paths.get("BookListDownload_" + dateTimeStr + ".csv"));
           Writer stockWriter = Files.newBufferedWriter(Paths.get("StockListDownload_" + dateTimeStr + ".csv"));
           Writer userWriter = Files.newBufferedWriter(Paths.get("UserListDownload_" + dateTimeStr + ".csv"));
           Writer lendingWriter = Files.newBufferedWriter(Paths.get("LendingListDownload_" + dateTimeStr + ".csv"));) {
        List<Book> bookList = bookService.selectAll();
        StatefulBeanToCsv<Book> bookToCsv = new StatefulBeanToCsvBuilder<Book>(bookWriter).build();
        bookToCsv.write(bookList);

        List<Stock> stockList = stockService.selectAll();
        StatefulBeanToCsv<Stock> stockToCsv = new StatefulBeanToCsvBuilder<Stock>(stockWriter).build();
        stockToCsv.write(stockList);

        List<User> userList = userService.selectAll();
        StatefulBeanToCsv<User> userToCsv = new StatefulBeanToCsvBuilder<User>(userWriter).build();
        userToCsv.write(userList);

        List<Lending> lendingList = lendingService.selectAll();
        StatefulBeanToCsv<Lending> lendingToCsv = new StatefulBeanToCsvBuilder<Lending>(lendingWriter).build();
        lendingToCsv.write(lendingList);
      }
      catch (IOException e) {
        System.err.println(e.toString());
        return false;
      }
      catch (Exception e) {
        System.err.println(e.toString());
        return false;
      }
      return true;
    }
}
