package com.idea.MyBook.Repository;

import com.idea.MyBook.Model.FavoriteList;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.ArrayList;

public interface FavoriteListRepository extends MongoRepository<FavoriteList, String> {
    ArrayList<FavoriteList> getAllByUserAccountId(String userAccountId);
    ArrayList<FavoriteList> getAllByUserAccountIdAndDeletedIsFalse(String userAccountId);
    FavoriteList getFavoriteListById(String id);
    Boolean existsFavoriteListById(String id);
}
