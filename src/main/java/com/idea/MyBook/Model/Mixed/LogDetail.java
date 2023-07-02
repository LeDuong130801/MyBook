package com.idea.MyBook.Model.Mixed;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;

@AllArgsConstructor
@Getter
@Setter
public class LogDetail {
    @Id
    String id;
    String bookId;
    String favoriteListId;
    String tagBookId;
    String userAccountId;
    public LogDetail(){
        id = "null";
        bookId = "null";
        favoriteListId = "null";
        tagBookId = "null";
        userAccountId = "null";
    }
}
