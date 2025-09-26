package org.example.alaa.mdcdemo.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LogContext {

    private String timestamp;
    private String correlationId;
    private String thread;
    private String level;
    private String logger;
    private String environment;

    private String httpMethod;
    private String uri;
    private Integer responseStatus;
    private Long responseTimMs;

    private Integer userId;
    private String userName;
    private Integer tenantId;
    private String role;

    private String errorType;
    private String errorMessage;
    private String stackTrace;
    private String rootCause;
}
