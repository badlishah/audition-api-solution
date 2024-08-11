package com.audition.service;

import com.audition.integration.AuditionIntegrationClient;
import com.audition.model.AuditionComment;
import com.audition.model.AuditionPost;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuditionService {

    @Autowired
    private AuditionIntegrationClient auditionIntegrationClient;


    public List<AuditionPost> getPosts() {
        return auditionIntegrationClient.getPosts();
    }

    public AuditionPost getPostById(final Integer postId) {
        return auditionIntegrationClient.getPostById(postId);
    }

    public List<AuditionComment> getCommentsParamPostId(final Integer postId) {
        return auditionIntegrationClient.getCommentsFromParamId(postId);
    }

    public List<AuditionComment> getCommentsVariablePostId(final Integer postId) {
        return auditionIntegrationClient.getCommentsFromVariableId(postId);
    }
}
