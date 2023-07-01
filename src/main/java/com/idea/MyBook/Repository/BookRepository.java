package com.idea.MyBook.Repository;

import com.idea.MyBook.Model.Book;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.ArrayList;

public interface BookRepository extends MongoRepository<Book, String> {
    public Book getBookById(String id);
    ArrayList<Book> findAllByDeletedFalse();
    public boolean existsBookById(String id);
}
