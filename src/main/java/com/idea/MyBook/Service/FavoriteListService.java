package com.idea.MyBook.Service;

import com.idea.MyBook.Model.ActivityLog;
import com.idea.MyBook.Model.FavoriteList;
import com.idea.MyBook.Model.TagBook;
import com.idea.MyBook.Repository.ActivityLogRepository;
import com.idea.MyBook.Repository.FavoriteListRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

@Service
@Slf4j
public class FavoriteListService {
    @Autowired
    FavoriteListRepository favoriteListRepository;
    @Autowired
    ActivityLogRepository activityLogRepository;
    int TYPE = 10002;
    int TYPE_BOOK = 10001;
    public void addBookToFavorite(String bookId, String favoriteListId){
        if(favoriteListRepository.existsFavoriteListById(favoriteListId)){
            FavoriteList favoriteList = favoriteListRepository.getFavoriteListById(favoriteListId);
            if(!favoriteList.getListBook().contains(bookId)){
                favoriteList.getListBook().add(bookId);
                createActivityLog("The favorite list "+favoriteList.getId()+" was added "+bookId, favoriteListId);
                createActivityLog("The favorite list "+favoriteList.getId()+" was added "+bookId, bookId, TYPE_BOOK);
            }
        }
    }

    public void createFavoriteList(FavoriteList favoriteList){
        favoriteList.setId(getUniqueFavoriteListId());
        favoriteList.setDeleted(false);
        createActivityLog("The favorite list "+favoriteList.getId()+" was created", favoriteList.getId());
    }
    public String editFavoriteList(FavoriteList favoriteList){
        if(favoriteListRepository.existsFavoriteListById(favoriteList.getId())){
            favoriteListRepository.save(favoriteList);
            createActivityLog("The favorite list "+favoriteList.getId()+" was edited", favoriteList.getId());
            return "200";
        }
        return "Not Found";
    }
    public String editFavoriteListName(String name, String favoriteListId){
        if(favoriteListRepository.existsFavoriteListById(favoriteListId)){
            FavoriteList favoriteList = favoriteListRepository.getFavoriteListById(favoriteListId);
            String oldName = favoriteList.getNameFavorite();
            favoriteList.setNameFavorite(name);
            createActivityLog("Favorite List "+favoriteListId+" is edited from "+oldName+" to "+name, favoriteListId);
            return "200";
        }
        return "Not Found";
    }
    public String deleteFavoriteList(String favoriteListId){
        if(favoriteListRepository.existsFavoriteListById(favoriteListId)){
            FavoriteList favoriteList = favoriteListRepository.getFavoriteListById(favoriteListId);
            if(!favoriteList.getDeleted()){
                favoriteList.setDeleted(true);
                favoriteListRepository.save(favoriteList);
                createActivityLog("The favorite list "+ favoriteListId + " was deleted", favoriteListId);
            }
            return "200";
        }
        return "Not Found";
    }
    public FavoriteList getFavoriteListById(String id){
        return favoriteListRepository.getFavoriteListById(id);
    }

    public ArrayList<FavoriteList> getAllFavoriteListByUserAccountId(String userAccountId){
        return favoriteListRepository.getAllByUserAccountIdAndDeletedIsFalse(userAccountId);
    }

    public String getUniqueFavoriteListId(){
        String id = generationId();
        for(boolean isUnique = false; isUnique; id = generationId()) {
            isUnique = favoriteListRepository.existsFavoriteListById(id);
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
