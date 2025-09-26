package org.example.alaa.mdcdemo;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.MDC;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.UUID;

@Component
@Order(1)
public class CorrelationIDFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String correlationId;
        try {
            correlationId = request.getHeader("X-Correlation-ID");
            if (correlationId == null) {
                correlationId = UUID.randomUUID().toString();
                request.setAttribute("X-Correlation-ID", correlationId); //add to request attributes for logging
                response.setHeader("X-Correlation-ID", correlationId); // add to response headers for clients
            }
            MDC.put("X-Correlation-ID", request.getAttribute("X-Correlation-ID").toString()); //add to MDC for logging
            filterChain.doFilter(request,response);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }finally {
            MDC.clear(); //clear MDC after request
        }

    }
}
