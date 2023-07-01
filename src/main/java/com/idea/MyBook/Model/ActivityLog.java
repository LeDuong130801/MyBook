package com.idea.MyBook.Model;

import com.idea.MyBook.Model.Mixed.LogDetail;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ActivityLog {
    @Id
    String id;
    String time;
    String content;
    LogDetail logDetail;
}
