package com.audition.common.logging;

import java.io.IOException;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class RestTemplateHeaderModifierInterceptor implements ClientHttpRequestInterceptor {

    private final transient AuditionLogger logger;

    public RestTemplateHeaderModifierInterceptor(final AuditionLogger logger) {
        this.logger = logger;
    }

    @Override
    public ClientHttpResponse intercept(final HttpRequest request, final byte[] body, final ClientHttpRequestExecution execution)
        throws IOException {
        final var messageRequest = "Request: " + request.getURI()
            + "-" + request.getMethod()
            + "-" + request.getHeaders();
        logger.info(log, messageRequest);
        final var response = execution.execute(request, body);
        final var messageResponse = "Response: " + response.getStatusCode()
            + "-" + response.getStatusText()
            + "-" + response.getHeaders();
        logger.info(log, messageResponse);
        return response;
    }
}
