package com.idea.MyBook.Service;

import com.idea.MyBook.Model.ActivityLog;
import com.idea.MyBook.Model.UserAccount;
import com.idea.MyBook.Repository.ActivityLogRepository;
import com.idea.MyBook.Repository.UserAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

@Service
public class UserAccountService {
    @Autowired
    UserAccountRepository userAccountRepository;
    @Autowired
    ActivityLogRepository activityLogRepository;
    int TYPE = 10005;
    public Integer createUserAccount(UserAccount userAccount){
        if(userAccountRepository.existsUserAccountByUsernameAndDeletedFalse(userAccount.getUsername())){
            return 2;
        }
        if(userAccountRepository.existsUserAccountByEmailAndDeletedFalse(userAccount.getEmail())){
            return 3;
        }
        if(userAccountRepository.existsUserAccountByPhoneNumberAndDeletedFalse(userAccount.getPhoneNumber())){
            return 4;
        }
        userAccount.setId(getUniqueUserAccountId());
        userAccount.setDeleted(false);
        userAccountRepository.insert(userAccount);
        createActivityLog("Account "+userAccount.getId()+" was created", userAccount.getId());
        return 1;
    }
    public String changePassword(String newPassword, String userAccountId){
        if(userAccountRepository.existsUserAccountById(userAccountId)){
            UserAccount userAccount = userAccountRepository.getUserAccountsById(userAccountId);
            userAccount.setHashPassword(newPassword);
            createActivityLog("Account "+userAccount.getId()+" was changed password", userAccount.getId());
            return "200";
        }
        return "Not Found";
    }
    public UserAccount getUserAccount(String username, String email, String phoneNumber){
        ArrayList<UserAccount> userAccountArrayList = userAccountRepository.getUserAccountsByUsernameOrEmailOrPhoneNumber(username, email, phoneNumber);
        if(userAccountArrayList == null){
            return null;
        }
        if(userAccountArrayList.size() == 0){
            return null;
        }
        for(int i=0;i<userAccountArrayList.size();i++){
            if(!userAccountArrayList.get(i).getDeleted()){
                return userAccountArrayList.get(i);
            }
        }
        return null;
    }

    public String getUniqueUserAccountId(){
        String id = generationId();
        for(boolean isUnique = false; isUnique; id = generationId()) {
            isUnique = userAccountRepository.existsUserAccountById(id);
        }
        return id;
    }
    private void createActivityLog(String content, String objectId){
        if (objectId == null){
            return;
        }
        Date date = new Date();
        String id = generationId();
        for(boolean isUnique = false; isUnique; id = generationId()) {
            isUnique = activityLogRepository.existsActivityLogById(id);
        }
        ActivityLog activityLog = new ActivityLog(id, date.toLocaleString(), content, TYPE, objectId);
        activityLogRepository.insert(activityLog);
    }
    private void createActivityLog(String content, String objectId, int type){
        if (objectId == null){
            return;
        }
        Date date = new Date();
        String id = generationId();
        for(boolean isUnique = false; isUnique; id = generationId()) {
            isUnique = activityLogRepository.existsActivityLogById(id);
        }
        ActivityLog activityLog = new ActivityLog(id, date.toLocaleString(), content, type, objectId);
        activityLogRepository.insert(activityLog);
    }
    private String generationId(){
        int leftLimit = 48; // letter '0'
        int rightLimit = 122; // letter 'z'
        //yes: 48-57, 65-90, 97-122
        int targetStringLength = 30;
        Random random = new Random();
        StringBuilder buffer = new StringBuilder(targetStringLength);
        int randomLimitedInt;
        for (int i = 0; i < targetStringLength; i++) {
            randomLimitedInt = leftLimit + Math.abs(random.nextInt()) % (rightLimit - leftLimit + 1);
            if((randomLimitedInt>57 && randomLimitedInt<65) || (randomLimitedInt>90 && randomLimitedInt<97)){
                i--;
                continue;
            }
            buffer.append((char) randomLimitedInt);
        }
        return buffer.toString();
    }
}
