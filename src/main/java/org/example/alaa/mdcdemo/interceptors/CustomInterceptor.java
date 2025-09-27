package org.example.alaa.mdcdemo.interceptors;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.alaa.mdcdemo.context.TenantContext;
import org.example.alaa.mdcdemo.model.LogContext;
import org.example.alaa.mdcdemo.model.TenantInfo;
import org.slf4j.Logger;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDateTime;

/**
 * CustomInterceptor demonstrates how to use Spring MVC interceptors
 * to handle request/response lifecycle events and enhance logging.
 *
 * Interceptor lifecycle:
 *  1. preHandle()→ Executed before the controller method.
 *  2. postHandle()→ Executed after controller returns, but before response is committed.
 *  3. afterCompletion()→ Executed after the entire request completes (response sent).
 *
 * Responsibilities of this interceptor:
 *  - Enrich logs with correlationId, tenant/user context, and timing info.
 *  - Set tenant/user data into ThreadLocal (TenantContext).
 *  - Use MDC (Mapped Diagnostic Context) for correlationId and timing.
 *  - Produce structured JSON logs (via LogContext).
 *  - Perform cleanup of thread-local data at the end.
 *
 * Why use this approach?
 *  - Correlation ID helps trace a request across multiple services.
 *  - TenantContext provides multi-tenancy/user-awareness during logging.
 *  - MDC ensures each request has its own thread-bound logging context.
 */
@Component
public class CustomInterceptor implements HandlerInterceptor {

    private static final Logger logger = org.slf4j.LoggerFactory.getLogger(CustomInterceptor.class);
    private static final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * Executed before the controller method.
     *
     * Use cases:
     *  - Authentication/authorization validation.
     *  - Request enrichment (e.g., set tenant/user context).
     *  - Short-circuit requests (return false to block further execution).
     *
     * @return true → continue with controller execution, false → abort request.
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        logger.info("PreHandler: Before Controller Request received for {}", request.getRequestURI());

        // Example of rejecting if no correlation id
        // if (request.getHeader("X-Correlation-ID") == null) {
        //     response.setStatus(HttpServletResponse.SC_NO_CONTENT);
        //     return false; // stop execution
        // }

        // Simulated user/tenant context (in real apps → populate from SecurityContext or JWT)
        TenantContext.setTenantInfo(new TenantInfo(1, "Alaa", 1));
        return true;
    }

    /**
     * Executed after controller returns, but before the response is written to the client.
     *
     * Use cases:
     *  - Modify ModelAndView (for MVC views).
     *  - Add/adjust response headers.
     *  - Inject metadata into response.
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response,
                           Object handler, ModelAndView modelAndView) throws Exception {
//        logger.info("PostHandler: After Controller for request# {}", response.getHeader("X-Correlation-ID"));
    }

    /**
     * Executed after the complete request is finished (response fully sent).
     *
     * Use cases:
     *  - Centralized request logging (success/failure).
     *  - Calculate request execution time.
     *  - Handle/log exceptions.
     *  - Cleanup (remove thread-locals like TenantContext).
     *
     * @param ex any exception thrown during request processing (null if none).
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
                                Object handler, Exception ex) throws Exception {

        LogContext logContext;

        // Only build log context if there was an exception (to avoid duplicate logging)
        if (ex == null) {
            logContext = LogContext.builder()
                    .timestamp(LocalDateTime.now().toString())
                    .correlationId(MDC.get("X-Correlation-ID"))
                    .level("INFO")
                    .environment("dev")
                    .logger(logger.getName())
                    .thread(Thread.currentThread().getName())
                    .httpMethod(request.getMethod())
                    .uri(request.getRequestURI())
                    .responseStatus(response.getStatus())
                    .responseTimMs(System.currentTimeMillis() - Long.parseLong(MDC.get("Start-Time")))
                    .userId(TenantContext.getTenantInfo().getUserId())
                    .userName(TenantContext.getTenantInfo().getUserName())
                    .tenantId(TenantContext.getTenantInfo().getTenantId())
                    .role("ROLE_USER")
                    .build();

            // Structured JSON log for better parsing in ELK/Datadog
            logger.info(objectMapper.writeValueAsString(logContext));
        }


        // Clean up tenant context (ThreadLocal)
        TenantContext.clear();
    }
}
