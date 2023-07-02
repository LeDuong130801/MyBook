package com.idea.MyBook.Repository;

import com.idea.MyBook.Model.TagBook;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.ArrayList;

public interface TagBookRepository extends MongoRepository<TagBook, String> {
    ArrayList<TagBook> getAllByDeletedIsFalse();
    TagBook getTagBookById(String id);
    Boolean existsTagBookById(String id);
}
