package com.idea.MyBook.Controller;

import com.idea.MyBook.Model.FavoriteList;
import com.idea.MyBook.Model.Mixed.MixedVal;
import com.idea.MyBook.Model.TagBook;
import com.idea.MyBook.Service.FavoriteListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping(value = "/api/account/favorite")
public class FavoriteListController {
    @Autowired
    FavoriteListService favoriteListService;

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String createFavoriteList(@RequestBody FavoriteList favoriteList){
        favoriteListService.createFavoriteList(favoriteList);
        return "200";
    }
    @RequestMapping(value = "/editname", method = RequestMethod.PUT)
    public String editFavoriteName(@RequestBody MixedVal object){
        //val1 is name
        //val2 is id
        return favoriteListService.editFavoriteListName(object.getVal1(), object.getVal2());
    }
    @RequestMapping(value = "/edit", method = RequestMethod.PUT)
    public String editFavorite(@RequestBody FavoriteList favoriteList){
        //val1 is name
        //val2 is id
        return favoriteListService.editFavoriteList(favoriteList);
    }
    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    public String deleteFavoriteList(@RequestBody MixedVal object){
        //val1 is id
        return favoriteListService.deleteFavoriteList(object.getVal1());
    }
    @RequestMapping(value = "/getlist", method = RequestMethod.GET)
    public ArrayList<FavoriteList> getAllFavoriteList(@RequestParam(value = "user") String userAccountId){
        return favoriteListService.getAllFavoriteListByUserAccountId(userAccountId);
    }
    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public FavoriteList getFavoriteListById(@RequestParam(value = "id") String favoriteListId){
        return favoriteListService.getFavoriteListById(favoriteListId);
    }
}
