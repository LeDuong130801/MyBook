package com.idea.MyBook.Service;

import com.idea.MyBook.Model.ActivityLog;
import com.idea.MyBook.Model.FavoriteList;
import com.idea.MyBook.Model.TagBook;
import com.idea.MyBook.Repository.ActivityLogRepository;
import com.idea.MyBook.Repository.TagBookRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Random;

@Service
@Slf4j
public class TagBookService {
    @Autowired
    TagBookRepository tagBookRepository;
    @Autowired
    ActivityLogRepository activityLogRepository;

    int TYPE = 10003;
    public void createTagBook(TagBook tagBook){
        tagBook.setId(getUniqueTagBookId());
        tagBook.setDeleted(false);
        tagBookRepository.insert(tagBook);
        createActivityLog("Tag book "+tagBook.getId()+" was created", tagBook.getId());
    }

    public String editTagBookName(String tagBookName, String tagBookId){
        if(tagBookRepository.existsTagBookById(tagBookId)){
            TagBook tagBook = tagBookRepository.getTagBookById(tagBookId);
            String oldName = tagBook.getTagName();
            tagBook.setTagName(tagBookName);
            createActivityLog("Tag Book "+tagBookId+" is edited from "+oldName+" to "+tagBookName, tagBookId);
            return "200";
        }
        return "Not Found";
    }

    public String deleteTagBookById(String tagBookId){
        if(tagBookRepository.existsTagBookById(tagBookId)){
            TagBook tagBook = tagBookRepository.getTagBookById(tagBookId);
            if(!tagBook.getDeleted()){
                tagBook.setDeleted(true);
                tagBookRepository.save(tagBook);
                createActivityLog("Tag Book "+ tagBookId + " was deleted", tagBookId);
            }
            return "200";
        }
        return "Not Found";
    }
    public String getUniqueTagBookId(){
        String id = generationId();
        for(boolean isUnique = false; isUnique; id = generationId()) {
            isUnique = tagBookRepository.existsTagBookById(id);
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
