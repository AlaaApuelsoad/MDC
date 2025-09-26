package org.example.alaa.mdcdemo.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.MDC;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.util.UUID;

@Component
@Order(1)
public class CorrelationIDFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) {
        long startTime = System.currentTimeMillis();
        String correlationId;
        try {
            correlationId = request.getHeader("X-Correlation-ID");
            if (correlationId == null) {
                correlationId = UUID.randomUUID().toString();
                request.setAttribute("X-Correlation-ID", correlationId); //add to request attributes for logging
                response.setHeader("X-Correlation-ID", correlationId); // add to response headers for clients
            }
            MDC.put("X-Correlation-ID", request.getAttribute("X-Correlation-ID").toString()); //add to MDC for logging
            MDC.put("Start-Time", String.valueOf(startTime)); //add request start time to MDC for logging

            filterChain.doFilter(request,response);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }finally {
            MDC.clear(); //clear MDC after request
        }

    }
}
