package com.idea.MyBook.Controller;

import com.idea.MyBook.Model.Mixed.MixedVal;
import com.idea.MyBook.Model.TagBook;
import com.idea.MyBook.Service.TagBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/tagbook")
@RestController
public class TagBookController {
    @Autowired
    TagBookService tagBookService;
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String createTagBook(@RequestBody TagBook tagBook){
        tagBookService.createTagBook(tagBook);
        return "200";
    }
    @RequestMapping(value = "/editname", method = RequestMethod.PUT)
    public String editTagBook(@RequestBody MixedVal object){
        //val1 is name
        //val2 is id
        return tagBookService.editTagBookName(object.getVal1(), object.getVal2());
    }
    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    public String deleteTagBookById(@RequestBody MixedVal object){
        //val1 is id
        return tagBookService.deleteTagBookById(object.getVal1());
    }
}
