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
public class Book {
    @Id
    String id;
    String bookName;
    String srcImage;
    ArrayList<String> tagBookId;
    Boolean deleted;
}
