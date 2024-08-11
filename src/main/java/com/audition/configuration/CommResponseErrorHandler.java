package com.audition.configuration;

import com.audition.common.exception.ClientException;
import com.audition.common.exception.ServerException;
import java.io.IOException;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResponseErrorHandler;

@Component
public class CommResponseErrorHandler implements ResponseErrorHandler {

    @Override
    public boolean hasError(ClientHttpResponse response) throws IOException {
        return response.getStatusCode().is4xxClientError() || response.getStatusCode().is5xxServerError();
    }

    @Override
    public void handleError(ClientHttpResponse response) throws IOException {
        if (response.getStatusCode().is4xxClientError()) {
            throw new ClientException(response.getStatusText(), response.getStatusCode().value());
        } else if (response.getStatusCode().is5xxServerError()) {
            throw new ServerException(response.getStatusText(), response.getStatusCode().value());
        }
    }
}
