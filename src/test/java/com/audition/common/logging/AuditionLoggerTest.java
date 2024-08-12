package com.audition.common.logging;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.net.URI;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;

@ExtendWith(MockitoExtension.class)
public class AuditionLoggerTest {

    @SuppressWarnings("PMD")
    private Logger mockLogger;
    private transient AuditionLogger auditionLogger;

    @BeforeEach
    void init() {
        mockLogger = mock(Logger.class);
        auditionLogger = new AuditionLogger();
    }

    @Test
    void loggingStandardProblemDetail() {

        final var problemDetail = ProblemDetail.forStatus(HttpStatus.FORBIDDEN);
        problemDetail.setDetail("forbidden");
        problemDetail.setInstance(URI.create("http://instance#"));
        problemDetail.setType(URI.create("http://type#"));
        final var exception = new Exception("loggingStandardProblemDetailException");
        final var messageCaptor = ArgumentCaptor.forClass(String.class);
        final var throwableCaptor = ArgumentCaptor.forClass(Throwable.class);

        when(mockLogger.isErrorEnabled()).thenReturn(true);
        auditionLogger.logStandardProblemDetail(mockLogger, problemDetail, exception);

        verify(mockLogger, times(1)).error(messageCaptor.capture(), throwableCaptor.capture());
        assertThat(messageCaptor.getValue()).isEqualTo("ProblemDetail[type='http://type#', title='Forbidden', status=403, detail='forbidden', instance='http://instance#', properties='null']");
        assertThat(throwableCaptor.getValue().getMessage()).isEqualTo(exception.getMessage());
    }
}
