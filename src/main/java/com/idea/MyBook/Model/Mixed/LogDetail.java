package com.idea.MyBook.Model.Mixed;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.annotation.Id;

@AllArgsConstructor
@Getter
public class LogDetail {
    @Id
    String id;
    String BookId;
    String FavoriteListId;
    String TagBookId;
    String UserAccountId;
}
