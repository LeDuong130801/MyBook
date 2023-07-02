package com.idea.MyBook.Controller;

import com.idea.MyBook.Model.Book;
import com.idea.MyBook.Model.Mixed.MixedObj;
import com.idea.MyBook.Model.TagBook;
import com.idea.MyBook.Service.BookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

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
    @RequestMapping(value = "/getlist", method = RequestMethod.POST)
    public ArrayList<Book> getListBook(@RequestBody MixedObj mixedObj){
        //val1 is name
        //obj1 is ArrTagBook
        String name = mixedObj.getVal1();
        ArrayList<String> tagBookId = mixedObj.getObj1();
        if(name.trim().equals("") && tagBookId.size()==0){
            return thisService.getAllBook();
        }
        return thisService.getBookByNameAndTagBookId(tagBookId, name);
    }
    @RequestMapping(value = "/editbook", method = RequestMethod.PUT)
    public String editBookName(@RequestBody Book book){
        if(thisService.editBookName(book.getBookName(), book.getId())){
            return "Edited";
        }
        return "Can't find book "+book.getId();
    }
    @RequestMapping(value = "/edit", method = RequestMethod.PUT)
    public String editBook(@RequestBody Book book){
        if(thisService.editBook(book)){
            return "Edited";
        }
        return "Can't find book "+book.getId();
    }
    @RequestMapping(value = "/edit/tag", method = RequestMethod.DELETE)
    public String removeTagInBook(@RequestBody MixedObj mixedObj){
        //val1 is bookId
        //obj1 is ArrTagBook
        ArrayList<String> tagBook = mixedObj.getObj1();
        String bookId = mixedObj.getVal1();
        thisService.removeTagBookInBook(tagBook, bookId);
        return "removed tagbook in book "+bookId;
    }
    @RequestMapping(value = "/edit/tag", method = RequestMethod.POST)
    public String addTagToBook(@RequestBody MixedObj mixedObj){
        //val1 is bookId
        //obj1 is ArrTagBook
        ArrayList<String> tagBook = mixedObj.getObj1();
        String bookId = mixedObj.getVal1();
        thisService.addNewTagBookToBook(tagBook, bookId);
        return "added tagbook in book "+bookId;
    }
}
