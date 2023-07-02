package com.idea.MyBook.Controller;

import com.idea.MyBook.Model.Mixed.MixedVal;
import com.idea.MyBook.Model.TagBook;
import com.idea.MyBook.Model.UserAccount;
import com.idea.MyBook.Service.UserAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/useraccount")
public class UserAccountController {
    @Autowired
    UserAccountService userAccountService;
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String createUserAccount(@RequestBody UserAccount userAccount){
        userAccountService.createUserAccount(userAccount);
        return "200";
    }
    @RequestMapping(value = "/changepassword", method = RequestMethod.PUT)
    public String changePassword(@RequestBody MixedVal object){
        //val1 is name
        //val2 is id
        return userAccountService.changePassword(object.getVal1(), object.getVal2());
    }
    @RequestMapping(value = "/processlogin", method = RequestMethod.PATCH)
    public String processLogin(@RequestBody MixedVal object){
        //val1 is username
        //val2 is email
        //val3 is phone
        //val4 is password
        UserAccount userAccount = userAccountService.getUserAccount(object.getVal1(), object.getVal2(), object.getVal3());
        if(userAccount!= null){
            if(object.getVal4().equals(userAccount.getHashPassword())){
                return userAccount.getId();
            }
        }
        return "0";
    }
}
