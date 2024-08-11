package com.audition.integration;

import com.audition.common.exception.SystemException;
import com.audition.model.AuditionComment;
import com.audition.model.AuditionPost;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
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
        // TODO make RestTemplate call to get Posts from https://jsonplaceholder.typicode.com/posts
        final String postUrl = baseUrl + "/" + postPath;
        final var responseEntity = restTemplate.getForEntity(postUrl, AuditionPost[].class);
        return Arrays.asList(Objects.requireNonNull(responseEntity.getBody()));
    }

    public AuditionPost getPostById(final String id) {
        // TODO get post by post ID call from https://jsonplaceholder.typicode.com/posts/
        try {
            final String postUrl = baseUrl + "/" + postPath + "/" + id;
            final var responseEntity = restTemplate.getForEntity(postUrl, AuditionPost.class);
            final var auditionPostItem = responseEntity.getBody();
            return auditionPostItem;
        } catch (final HttpClientErrorException e) {
            if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
                throw new SystemException("Cannot find a Post with id " + id, "Resource Not Found",
                    404);
            } else {
                // TODO Find a better way to handle the exception so that the original error message is not lost. Feel free to change this function.
                throw new SystemException("Unknown Error message");
            }
        }
    }

    // TODO Write a method GET comments for a post from https://jsonplaceholder.typicode.com/posts/{postId}/comments - the comments must be returned as part of the post.
    public List<AuditionComment> getCommentsFromVariableId(final String postId) {
        final String commentsUrl = baseUrl + "/"
            + postPath
            + "/"
            + postId
            + "/"
            + commentPath;
        final var responseEntity = restTemplate.getForEntity(commentsUrl, AuditionComment[].class);
        return Arrays.asList(Objects.requireNonNull(responseEntity.getBody()));
    }

    // TODO write a method. GET comments for a particular Post from https://jsonplaceholder.typicode.com/comments?postId={postId}.
    // The comments are a separate list that needs to be returned to the API consumers. Hint: this is not part of the AuditionPost pojo.
    public List<AuditionComment> getCommentsFromParamId(final String postId) {
        String commentsUrl = baseUrl + "/"
            + commentPath
            + "?postId="
            + postId;
        final var responseEntity = restTemplate.getForEntity(commentsUrl, AuditionComment[].class);
        return Arrays.asList(Objects.requireNonNull(responseEntity.getBody()));
    }
}
