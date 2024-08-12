package com.audition.common.logging;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;

@Component
@Slf4j
public class RestTemplateHeaderModifierInterceptor implements ClientHttpRequestInterceptor {

    private final transient AuditionLogger logger;

    public RestTemplateHeaderModifierInterceptor(final AuditionLogger logger){
        this.logger = logger;
    }

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution)
        throws IOException {
        final var messageRequest = "Request: "+request.getURI()
            +"-"+request.getMethod()
            +"-"+request.getHeaders();
        logger.info(log, messageRequest);
        final var response = execution.execute(request, body);
        final var messageResponse = "Response: "+response.getStatusCode()
            +"-"+response.getStatusText()
            +"-"+response.getHeaders();
        logger.info(log, messageResponse);
        return response;
    }
}
