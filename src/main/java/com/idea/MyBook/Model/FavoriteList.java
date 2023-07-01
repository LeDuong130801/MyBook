package com.idea.MyBook.Model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class FavoriteList {
    String id;
    String userAccountId;
    String nameFavorite;
    ArrayList<String> listBook;
}
