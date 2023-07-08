package com.idea.MyBook.Model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import java.util.ArrayList;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserAccount {
    @Id
    String id;
    String username;
    String hashPassword;
    String favoriteId;
    String email;
    String phoneNumber;
    String avatarSrc;
    Boolean deleted;
}
