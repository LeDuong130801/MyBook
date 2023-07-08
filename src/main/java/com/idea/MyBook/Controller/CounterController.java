package com.idea.MyBook.Controller;

import com.idea.MyBook.Model.Mixed.MixedVal;
import com.idea.MyBook.Service.CounterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequestMapping("/api/count")
public class CounterController {
    @Autowired
    CounterService counterService;
    @GetMapping(value = "/book")
    public MixedVal countBook(){
        MixedVal mixedVal = new MixedVal();
        mixedVal.setVal1(counterService.countBook().toString());
        return mixedVal;
    }
    @GetMapping(value="/allbook")
    public String countAllBook(){
        return counterService.countAllOfBook().toString();
    }
    @GetMapping(value="/deletedbook")
    public String countDeletedBook(){
        return counterService.countDeletedBook().toString();
    }
    @GetMapping(value="/favoritelist")
    public String countFavoriteList(){
        return counterService.countFavorite().toString();
    }
    @GetMapping(value="/tagbook")
    public String countTagBook(){
        return counterService.countTagBook().toString();
    }
    @GetMapping(value="/useraccount")
    public String countUserAccount(){
        return counterService.countUserAccount().toString();
    }

}
