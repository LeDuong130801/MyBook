package com.idea.MyBook.Service;

import com.idea.MyBook.Model.ActivityLog;
import com.idea.MyBook.Model.Book;
import com.idea.MyBook.Repository.ActivityLogRepository;
import com.idea.MyBook.Repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

@Service
public class BookService{
    final
    BookRepository thisRepository;
    final
    ActivityLogRepository activityLogRepository;
    int TYPE = 10001;
    public BookService(BookRepository thisRepository, ActivityLogRepository activityLogRepository) {
        this.thisRepository = thisRepository;
        this.activityLogRepository = activityLogRepository;
    }

    public void addNewBook(Book book){
        book.setId(getUniqueBookId());
        book.setDeleted(false);
        thisRepository.insert(book);
        createActivityLog("Book "+book.getId()+" was created", book.getId());
    }

    public boolean editBookName(String bookName, String bookId){
        String oldName = "";
        if(thisRepository.existsBookById(bookId)){
            Book thisBook = thisRepository.getBookById(bookId);
            oldName = thisBook.getBookName();
            thisBook.setBookName(bookName);
            thisRepository.save(thisBook);
        }
        else return false;
        createActivityLog("Book "+bookId+" was edited from "+oldName+" to "+bookName, bookId);
        return true;
    }
    public boolean editBook(Book book){
        if(thisRepository.existsBookById(book.getId())){
            thisRepository.save(book);
            createActivityLog("Book "+book.getId()+" was edited", book.getId());
        }
        else return false;
        return true;
    }

    public boolean removeBook(ArrayList<String> bookId){
        for(int i=0;i<bookId.size();i++){
            if(thisRepository.existsBookById(bookId.get(i))){
                Book thisBook = thisRepository.getBookById(bookId.get(i));
                if(thisBook.getDeleted())
                thisBook.setDeleted(true);
                thisRepository.save(thisBook);
                createActivityLog("Book "+bookId+" was deleted", bookId.get(i));
            }
        }
        return true;
    }

    public boolean addNewTagBookToBook(ArrayList<String> tagBookId, String bookId){
        if(!thisRepository.existsBookById(bookId)){
            return false;
        }
        Book thisBook = thisRepository.getBookById(bookId);
        if(thisBook.getDeleted()){
            return false;
        }
        for(int i=0;i<tagBookId.size();i++){
            if(!thisBook.getTagBookId().contains(tagBookId.get(i))){
                thisBook.getTagBookId().add(tagBookId.get(i));
                createActivityLog("Book "+bookId+" was added tag "+tagBookId.get(i), bookId);
            }
        }
        thisRepository.save(thisBook);
        return true;
    }
    public boolean removeTagBookInBook(ArrayList<String> tagBookId, String bookId){
        if(!thisRepository.existsBookById(bookId)){
            return false;
        }
        Book thisBook = thisRepository.getBookById(bookId);
        if(thisBook.getDeleted()){
            return false;
        }
        for(int i=0;i<tagBookId.size();i++){
            thisBook.getTagBookId().remove(tagBookId.get(i));
            createActivityLog("Book "+bookId+" was removed tag "+tagBookId.get(i), bookId);
        }
        thisRepository.save(thisBook);
        return true;
    }
    public Book getBookById(String id){
        return thisRepository.getBookById(id);
    }
//    public ArrayList<Book> getBookByTagBookId(ArrayList<String> tagBookId){
//        return thisRepository.findAllByDeletedFalseAndTagBookIdContains(tagBookId);
//    }
//    public ArrayList<Book> getBookByName(String name){
//        return thisRepository.findAllByDeletedFalseAndBookNameLike(name);
//    }
    public ArrayList<Book> getBookByNameAndTagBookId(ArrayList<String> tagBookId, String name){
        return thisRepository.findAllByDeletedFalseAndTagBookIdContainsAndBookNameLike(tagBookId, name);
    }
    public ArrayList<Book> getAllBook(){
        return thisRepository.findAllByDeletedFalse();
    }
    public String getUniqueBookId(){
        String id = generationId();
        for(boolean isUnique = false; isUnique; id = generationId()) {
            isUnique = thisRepository.existsBookById(id);
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
