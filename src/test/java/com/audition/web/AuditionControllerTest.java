package com.audition.web;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import com.audition.service.AuditionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

class AuditionControllerTest {

    private static final Random RANDOM = new Random();
    private transient AuditionController controller;
    private transient AuditionService service;

    @BeforeEach
    void init() {
        service = mock(AuditionService.class);
        controller = new AuditionController(service);
    }

    @Test
    void testSinglePost() {
        final var postIdCaptor = ArgumentCaptor.forClass(Integer.class);
        final var postId = randomInt();
        controller.getPosts(postId);
        verify(service).getPostById(postIdCaptor.capture());
        assertEquals(postId, postIdCaptor.getValue());
    }

    @Test
    void testGetCommentsFromParam() {
        final var postIdCaptor = ArgumentCaptor.forClass(Integer.class);
        final var postId = randomInt();
        controller.getCommentsFromRequestParamId(postId);
        verify(service).getCommentsParamPostId(postIdCaptor.capture());
        assertEquals(postId, postIdCaptor.getValue());
    }

    @Test
    void testGetCommentsFromPath() {
        final var postIdCaptor = ArgumentCaptor.forClass(Integer.class);
        final var postId = randomInt();
        controller.getCommentsFromPathVariableId(postId);
        verify(service).getCommentsVariablePostId(postIdCaptor.capture());
        assertEquals(postId, postIdCaptor.getValue());
    }

    private int randomInt() {
        return RANDOM.nextInt();
    }

}
