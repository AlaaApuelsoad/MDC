package org.example.alaa.mdcdemo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.alaa.mdcdemo.context.TenantContext;
import org.example.alaa.mdcdemo.model.LogContext;
import org.slf4j.MDC;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.Arrays;

@ControllerAdvice
public class Handler {

    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(Handler.class);
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception e, HttpServletRequest request, HttpServletResponse response) throws JsonProcessingException {
        LogContext logContext = LogContext.builder()
                .timestamp(LocalDateTime.now().toString())
                .correlationId(MDC.get("X-Correlation-ID"))
                .level("ERROR")
                .environment("dev")
                .logger(logger.getName())
                .thread(Thread.currentThread().getName())
                .httpMethod(request.getMethod())
                .uri(request.getRequestURI())
                .responseStatus(HttpStatus.BAD_REQUEST.value())
                .responseTimMs(System.currentTimeMillis() - Long.parseLong(MDC.get("Start-Time")))
                .userId(TenantContext.getTenantInfo().getUserId())
                .userName(TenantContext.getTenantInfo().getUserName())
                .tenantId(TenantContext.getTenantInfo().getTenantId())
                .role("ROLE_USER")
                .errorType(e.getClass().getSimpleName())
                .errorMessage(e.getMessage())
                .rootCause(e.getCause() != null ? e.getCause().getMessage() : null)
                .stackTrace(getStackTrace(e))
                .build();
        logger.error(objectMapper.writeValueAsString(logContext));
        return ResponseEntity.badRequest().body(e.getMessage());
    }


    private String getStackTrace(Exception exception) {
        StackTraceElement[] stackTraceElements = exception.getStackTrace();
        if (stackTraceElements.length > 0) {
            StackTraceElement origin = stackTraceElements[0];
            String className = origin.getClassName();
            String methodName = origin.getMethodName();
            int lineNumber = origin.getLineNumber();
            return className + "." + methodName + "(" + lineNumber + ")";
        }
        return "Unknown Origin";
    }
}
