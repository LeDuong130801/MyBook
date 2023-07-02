package com.idea.MyBook.Repository;

import com.idea.MyBook.Model.ActivityLog;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.ArrayList;

public interface ActivityLogRepository extends MongoRepository<ActivityLog, String> {
    ArrayList<ActivityLog> getAllByContentIsLike(String content);
    ArrayList<ActivityLog> getAllByTimeIsLike(String time);
    ActivityLog getActivityLogById(String id);
    boolean existsActivityLogById(String id);
}
