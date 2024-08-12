package com.audition.configuration;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class ResponseHeaderInjector extends OncePerRequestFilter {
    private static final String TRACE_ID = "X-openTelemetryTraceId";
    private static final String SPAN_ID = "X-openTelemetrySpanId";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
        throws ServletException, IOException {
        response.setHeader(TRACE_ID, "-");
        response.setHeader(SPAN_ID, "-");
        filterChain.doFilter(request, response);
    }
}
