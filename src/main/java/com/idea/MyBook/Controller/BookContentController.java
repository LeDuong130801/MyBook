package com.idea.MyBook.Controller;

import com.idea.MyBook.Model.BookContent;
import com.idea.MyBook.Model.Mixed.MixedObj;
import com.idea.MyBook.Model.UploadResponse;
import com.idea.MyBook.Service.BookContentService;
import com.idea.MyBook.Service.FileStorageBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/content")
public class BookContentController {
    @Autowired
    BookContentService bookContentService;
    @Autowired
    FileStorageBookService fileStorageBookService;

    @RequestMapping(value = "/addwithimg", method = RequestMethod.POST)
    public ResponseEntity<String> addContentWithImage(@RequestParam(name = "file", required = false) MultipartFile file, @RequestBody BookContent bookContent) {
        String fileName = fileStorageBookService.storeFile(file);
        UploadResponse uploadResponse = new UploadResponse(fileName);
        bookContent.setContent(fileName);
        bookContentService.addContent(bookContent);
        return ResponseEntity.ok().body(uploadResponse.getFileName());
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseEntity<String> addContent(@RequestBody BookContent bookContent){
        bookContentService.addContent(bookContent);
        return ResponseEntity.ok().body(bookContent.getId());
    }

    @RequestMapping(value = "/editwithimg", method = RequestMethod.PUT)
    public ResponseEntity<String> editContentWithImg(@RequestParam(name = "file", required = false) MultipartFile file, @RequestBody BookContent bookContent){
        String fileName = fileStorageBookService.storeFile(file);
        UploadResponse uploadResponse = new UploadResponse(fileName);
        bookContent.setContent(fileName);
        bookContentService.editContent(bookContent);
        return ResponseEntity.ok().body(uploadResponse.getFileName());
    }
    @RequestMapping(value = "/edit", method = RequestMethod.PUT)
    public ResponseEntity<String> editContent(@RequestBody BookContent bookContent){
        return bookContentService.editContent(bookContent)?
                ResponseEntity.ok().body("200"):ResponseEntity.ok().body("404");
    }
    @RequestMapping(value = "/remove", method = RequestMethod.DELETE)
    public ResponseEntity<String> removeContent(@RequestBody MixedObj mixedObj){
        bookContentService.removeContent(mixedObj.getObj1());
        return ResponseEntity.ok().body("removed");
    }
}
