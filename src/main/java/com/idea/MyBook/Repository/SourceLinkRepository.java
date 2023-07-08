package com.idea.MyBook.Repository;

import com.idea.MyBook.Model.SourceLink;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SourceLinkRepository extends MongoRepository<SourceLink, String> {
    public SourceLink getSourceLinkById(String id);
    public SourceLink getSourceLinkByUrl(String url);
    public boolean existsSourceLinkById(String id);
}
