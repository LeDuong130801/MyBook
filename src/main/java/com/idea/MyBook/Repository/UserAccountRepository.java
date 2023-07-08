package com.idea.MyBook.Repository;

import com.idea.MyBook.Model.UserAccount;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.ArrayList;

public interface UserAccountRepository extends MongoRepository<UserAccount, String> {
    ArrayList<UserAccount> getUserAccountsByUsernameOrEmailOrPhoneNumber(String username, String email, String phoneNumber);
    Boolean existsUserAccountByUsernameAndDeletedFalse(String username);
    Boolean existsUserAccountByPhoneNumberAndDeletedFalse(String phoneNumber);
    Boolean existsUserAccountByEmailAndDeletedFalse(String email);
    ArrayList<UserAccount> getUserAccountsByDeleted(Boolean deleted);
    UserAccount getUserAccountsById(String id);
    Boolean existsUserAccountById(String id);
    ArrayList<UserAccount> findAllByDeletedFalseOrDeletedTrue();
    ArrayList<UserAccount> findAllByDeletedFalse();
    ArrayList<UserAccount> findAllByDeletedTrue();
}
