package com.idea.MyBook.Repository;

import com.idea.MyBook.Model.ActivityLog;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.ArrayList;

public interface ActivityLogRepository extends MongoRepository<ActivityLog, String> {
    ArrayList<ActivityLog> getAllByContentIsLike(String content);
    ArrayList<ActivityLog> getAllByTimeIsLike(String time);
    ArrayList<ActivityLog> getAllByLogDetail_BookId(String bookId);
    ArrayList<ActivityLog> getAllByLogDetail_FavoriteListId(String favoriteListId);
    ArrayList<ActivityLog> getAllByLogDetail_TagBookId(String tagBookId);
    ArrayList<ActivityLog> getAllByLogDetail_UserAccountId(String userAccountId);
    ActivityLog getActivityLogById(String id);
    boolean existsActivityLogById(String id);
}
