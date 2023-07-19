package com.idea.MyBook.Service;

import com.idea.MyBook.Model.ActivityLog;
import com.idea.MyBook.Model.BookContent;
import com.idea.MyBook.Repository.ActivityLogRepository;
import com.idea.MyBook.Repository.BookContentRepository;
import com.idea.MyBook.Repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

@Service
public class BookContentService {
    @Autowired
    BookContentRepository bookContentRepository;
    @Autowired
    BookRepository bookRepository;
    @Autowired
    ActivityLogRepository activityLogRepository;
    int TYPE = 10000;
    int TYPE_BOOK = 10001;
    public ArrayList<BookContent> getAllContentOfBook(String bookId){
        return bookContentRepository.getAllByBookId(bookId);
    }

    public BookContent getBookContentById(String id){
        return bookContentRepository.getBookContentById(id);
    }

    public String getContentById(String id){
        if (bookContentRepository.existsById(id)){
            return bookContentRepository.getBookContentById(id).getContent();
        }
        return "404";
    }
    public void addContent(BookContent bookContent){
        String id = getUniqueBookId();
        bookContent.setId(id);
        bookContentRepository.insert(bookContent);
        createActivityLog("Content of Book "+bookContent.getBookId()+" is added", bookContent.getBookId(), TYPE_BOOK);
        createActivityLog("Content "+bookContent.getId()+" is added", bookContent.getId(), TYPE);
    }
    public boolean editContent(BookContent bookContent){
        if(bookContentRepository.existsBookContentById(bookContent.getId())){
            bookContentRepository.save(bookContent);
            createActivityLog("Content "+bookContent.getId()+" is edited", bookContent.getId(), TYPE);
            return true;
        }
        return false;
    }
    public void removeContent(ArrayList<String> listIdContent){
        for (String s : listIdContent) {
            if (bookContentRepository.existsBookContentById(s)) {
                BookContent bookContent = bookContentRepository.getBookContentById(s);
                bookContent.setIndex(-1);
                bookContentRepository.save(bookContent);
                createActivityLog("Content "+bookContent.getId()+" is removed", s, TYPE);
            }
        }
    }
    public String getUniqueBookId(){
        String id = generationId();
        for(boolean isUnique = false; isUnique; id = generationId()) {
            isUnique = bookContentRepository.existsBookContentById(id);
        }
        return id;
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
