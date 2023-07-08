package com.idea.MyBook.Service;

import com.idea.MyBook.Repository.BookRepository;
import com.idea.MyBook.Repository.FavoriteListRepository;
import com.idea.MyBook.Repository.TagBookRepository;
import com.idea.MyBook.Repository.UserAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CounterService {
    @Autowired
    BookRepository bookRepository;
    @Autowired
    FavoriteListRepository favoriteListRepository;
    @Autowired
    TagBookRepository tagBookRepository;
    @Autowired
    UserAccountRepository userAccountRepository;

    public Integer countAllOfBook(){
        return bookRepository.findAllByDeletedFalseOrDeletedTrue().size();
    }
    public Integer countDeletedBook(){
        return bookRepository.findAllByDeletedTrue().size();
    }
    public Integer countBook(){
        return bookRepository.findAllByDeletedFalse().size();
    }
    public Integer countAllOfTagBook(){
        return tagBookRepository.findAllByDeletedFalseOrDeletedTrue().size();
    }
    public Integer countDeletedTagBook(){
        return tagBookRepository.findAllByDeletedTrue().size();
    }
    public Integer countTagBook(){
        return tagBookRepository.findAllByDeletedFalse().size();
    }
    public Integer countAllOfFavorite(){
        return favoriteListRepository.findAllByDeletedFalseOrDeletedTrue().size();
    }
    public Integer countDeletedFavorite(){
        return favoriteListRepository.findAllByDeletedTrue().size();
    }
    public Integer countFavorite(){
        return favoriteListRepository.findAllByDeletedFalse().size();
    }
    public Integer countUserAccount(){
        return userAccountRepository.findAllByDeletedFalse().size();
    }
}
