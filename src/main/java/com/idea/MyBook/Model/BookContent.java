package com.idea.MyBook.Model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BookContent {
    @Id
    String id;
    Integer index; //-1 = xoa, 0-?: thu tu
    String content; //html src hoac chu
    String bookId;
}
