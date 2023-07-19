package com.idea.MyBook.Controller;

import com.idea.MyBook.Model.Book;
import com.idea.MyBook.Model.Mixed.MixedObj;
import com.idea.MyBook.Model.TagBook;
import com.idea.MyBook.Model.UploadResponse;
import com.idea.MyBook.Service.BookService;
import com.idea.MyBook.Service.FileStorageBookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;

@RestController
@RequestMapping(value = "/api/book")
@Slf4j
public class BookController {
    @Autowired
    BookService thisService;
    @Autowired
    FileStorageBookService fileStorageBookService;
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
    @RequestMapping(value = "/getlist", method = RequestMethod.GET)
    public ArrayList<Book> getListBook(){
        return thisService.getAllBook();
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
    @RequestMapping(value = "/edittag", method = RequestMethod.DELETE)
    public String removeTagInBook(@RequestBody MixedObj mixedObj){
        //val1 is bookId
        //obj1 is ArrTagBook
        ArrayList<String> tagBook = mixedObj.getObj1();
        String bookId = mixedObj.getVal1();
        thisService.removeTagBookInBook(tagBook, bookId);
        return "removed tagbook in book "+bookId;
    }
    @RequestMapping(value = "/edittag", method = RequestMethod.POST)
    public String addTagToBook(@RequestBody MixedObj mixedObj){
        //val1 is bookId
        //obj1 is ArrTagBook
        ArrayList<String> tagBook = mixedObj.getObj1();
        String bookId = mixedObj.getVal1();
        thisService.addNewTagBookToBook(tagBook, bookId);
        return "added tagbook in book "+bookId;
    }
    @PostMapping("/uploadbook")
    public ResponseEntity<String> uploadFile( @RequestParam(name = "file", required = false) MultipartFile file, @RequestBody Book book) {
        String fileName = fileStorageBookService.storeFile(file);
        UploadResponse uploadResponse = new UploadResponse(fileName);
        book.setSrcImage(fileName);
        thisService.addNewBook(book);
        return ResponseEntity.ok().body(uploadResponse.getFileName());
    }
    @PostMapping("/uploadbookimg")
    public ResponseEntity<String> uploadBookImg( @RequestParam(name = "file", required = false) MultipartFile file,@RequestParam(name="bookId") String bookId) {
        String fileName = fileStorageBookService.storeFile(file);
        UploadResponse uploadResponse = new UploadResponse(fileName);
        thisService.updateBookImg(fileName, bookId);
        return ResponseEntity.ok().body(uploadResponse.getFileName());
    }

}
