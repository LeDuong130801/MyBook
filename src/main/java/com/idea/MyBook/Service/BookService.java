package com.idea.MyBook.Service;

import com.idea.MyBook.Model.Book;
import com.idea.MyBook.Model.TagBook;
import com.idea.MyBook.Repository.BookRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Service;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

@Service
public class BookService{
    final
    BookRepository thisRepository;

    public BookService(BookRepository thisRepository) {
        this.thisRepository = thisRepository;
    }

    public boolean addNewBook(Book book){
        book.setId(getUniqueBookId());
        book.setDeleted(false);
        thisRepository.insert(book);
        return true;
    }

    public boolean editBookName(String bookName, String bookId){
        if(thisRepository.existsBookById(bookId)){
            Book thisBook = thisRepository.getBookById(bookId);
            thisBook.setBookName(bookName);
            thisRepository.save(thisBook);
        }
        else return false;
        return true;
    }

    public boolean removeBook(ArrayList<String> bookId){
        for(int i=0;i<bookId.size();i++){
            if(thisRepository.existsBookById(bookId.get(i))){
                Book thisBook = thisRepository.getBookById(bookId.get(i));
                thisBook.setDeleted(true);
                thisRepository.save(thisBook);
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
            }
        }
        thisRepository.save(thisBook);
        return true;
    }
    public boolean removeTagBookFromBook(ArrayList<String> tagBookId, String bookId){
        if(!thisRepository.existsBookById(bookId)){
            return false;
        }
        Book thisBook = thisRepository.getBookById(bookId);
        if(thisBook.getDeleted()){
            return false;
        }
        for(int i=0;i<tagBookId.size();i++){
            thisBook.getTagBookId().remove(tagBookId.get(i));
        }
        thisRepository.save(thisBook);
        return true;
    }
    public String getUniqueBookId(){
        String id = generationId();
        for(boolean isUnique = false; isUnique; id = generationId()) {
            isUnique = thisRepository.existsBookById(id);
        }
        return id;
    }
    public Book getBookById(String id){
        return thisRepository.getBookById(id);
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
