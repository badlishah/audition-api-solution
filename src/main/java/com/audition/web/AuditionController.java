package com.audition.web;

import com.audition.model.AuditionComment;
import com.audition.model.AuditionPost;
import com.audition.service.AuditionService;
import jakarta.validation.constraints.Positive;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated
public class AuditionController {

    @Autowired
    private final transient AuditionService auditionService;

    public AuditionController(final AuditionService auditionService) {
        this.auditionService = auditionService;
    }

    @RequestMapping(value = "/posts", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody List<AuditionPost> getPosts() {
        return auditionService.getPosts();
    }

    @RequestMapping(value = "/posts/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody AuditionPost getPosts(@PathVariable("id")
        @Positive final Integer postId) {
        return  auditionService.getPostById(postId);
    }

    @RequestMapping(value = "/posts/{id}/comments", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody List<AuditionComment> getCommentsFromPathVariableId(@PathVariable(name = "id", required = true)
        @Positive final Integer postId) {
        return auditionService.getCommentsVariablePostId(postId);
    }

    @RequestMapping(value = "/comments", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody List<AuditionComment> getCommentsFromRequestParamId(@RequestParam(name = "postId", required = true)
        @Positive final Integer postId) {
        return auditionService.getCommentsParamPostId(postId);
    }


}
