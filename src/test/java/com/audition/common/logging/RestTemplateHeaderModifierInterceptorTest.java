package com.audition.common.logging;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.HttpRequest;
import org.mockito.ArgumentCaptor;

@Slf4j
public class RestTemplateHeaderModifierInterceptorTest {
    private  transient RestTemplateHeaderModifierInterceptor interceptor;
    private  transient AuditionLogger logger;
    private  transient HttpRequest request;
    private  transient ClientHttpRequestExecution execution;
    private  transient ClientHttpResponse response;
    private  transient byte[] body;

    @BeforeEach
    void init() {
        logger = mock(AuditionLogger.class);
        request = mock(HttpRequest.class);
        response = mock(ClientHttpResponse.class);
        body = "".getBytes(UTF_8);
        execution = mock(ClientHttpRequestExecution.class);
    }

    @Test
    void loggingRequestAndResponse() throws IOException {
        when(execution.execute(any(), any())).thenReturn(response);
        interceptor = new RestTemplateHeaderModifierInterceptor(logger);
        final var argumentCaptor = ArgumentCaptor.forClass(String.class);
        interceptor.intercept(request, body, execution);
        verify(logger, times(2)).info(any(), argumentCaptor.capture());
        log.info(argumentCaptor.getAllValues().get(1));
        assertTrue(argumentCaptor.getAllValues().get(0).contains("Request:"));
        assertTrue(argumentCaptor.getAllValues().get(1).contains("Response:"));
    }

}
