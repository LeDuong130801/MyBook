package com.idea.MyBook.Repository;

import com.idea.MyBook.Model.BookContent;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.ArrayList;

public interface BookContentRepository extends MongoRepository<BookContent, String> {
    BookContent getBookContentById(String id);
    ArrayList<BookContent> getAllByBookId(String bookId);
}
