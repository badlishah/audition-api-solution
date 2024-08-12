package com.audition.integration;

import com.audition.model.AuditionComment;
import com.audition.model.AuditionPost;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Objects;

@Component
public class AuditionIntegrationClient {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${application.config.url.base}")
    String baseUrl;

    @Value("${application.config.url.postsPath}")
    String postPath;

    @Value("${application.config.url.commentsPath}")
    String commentPath;

    public List<AuditionPost> getPosts() {
        final String postUrl = baseUrl + "/" + postPath;
        final var responseEntity = restTemplate.getForEntity(postUrl, AuditionPost[].class);
        return Arrays.asList(Objects.requireNonNull(responseEntity.getBody()));
    }

    public AuditionPost getPostById(final Integer id) {
        final String postUrl = baseUrl + "/" + postPath + "/" + id;
        final var responseEntity = restTemplate.getForEntity(postUrl, AuditionPost.class);
        return responseEntity.getBody();
    }

    // TODO Write a method GET comments for a post from https://jsonplaceholder.typicode.com/posts/{postId}/comments - the comments must be returned as part of the post.
    public List<AuditionComment> getCommentsFromVariableId(final Integer postId) {
        final String commentsUrl = baseUrl + "/"
            + postPath
            + "/"
            + postId
            + "/"
            + commentPath;
        final var responseEntity = restTemplate.getForEntity(commentsUrl, AuditionComment[].class);
        return Arrays.asList(Objects.requireNonNull(responseEntity.getBody()));
    }

    // The comments are a separate list that needs to be returned to the API consumers. Hint: this is not part of the AuditionPost pojo.
    public List<AuditionComment> getCommentsFromParamId(final Integer postId) {
        String commentsUrl = baseUrl + "/"
            + commentPath
            + "?postId="
            + postId;
        final var responseEntity = restTemplate.getForEntity(commentsUrl, AuditionComment[].class);
        return Arrays.asList(Objects.requireNonNull(responseEntity.getBody()));
    }
}
