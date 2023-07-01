package com.idea.MyBook.Controller;

import com.idea.MyBook.Model.Book;
import com.idea.MyBook.Service.BookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/book")
@Slf4j
public class BookController {
    @Autowired
    BookService thisService;
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String addBook(@RequestBody Book book){
        thisService.addNewBook(book);
        return "Added New Book";
    }
    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public Book getBook(@RequestParam String id){
        log.info("Getting book: "+id);
        return thisService.getBookById(id);
    }
}
