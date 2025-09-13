package org.example.alaa.mdcdemo;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Component
public class CustomInterceptor implements HandlerInterceptor {
    private static final Logger logger = org.slf4j.LoggerFactory.getLogger(CustomInterceptor.class);
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
            /*
           Called before the controller method executes,
           You can:
           Validate authentication/authorization.
           Reject a request early (return false stops execution).
           Add request attributes for later use.
             */
        logger.info("PreHandler: Before Controller Request received for {}", request.getRequestURI());

        // Example: block request if no token
//        if (request.getHeader("X-Correlation-ID") == null) {
//            response.setStatus(HttpServletResponse.SC_NO_CONTENT);
//            return false; // stop execution
//        }
        //should fill from securityContext
        TenantContext.setTenantInfo(new TenantInfo(1,"Alaa",1));
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        /*
        Called after the controller method executes, but before the response is rendered (before View or response body is written).
        You can:
        Add/modify attributes in ModelAndView (for MVC views).
        Adjust response headers.
        Inject metadata into response.
         */
        logger.info("PostHandler: After Controller for request#  {}", response.getHeader("X-Correlation-ID"));
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

        /*
        Called after the entire request is completed → once the response is fully rendered and sent to the client.
        You can:
        Do cleanup work (close resources).
        Log final status.
        Track request execution time.
        Handle exceptions (if any occurred).
         */
//        logger.info("AfterCompletion: Request# ,{} finished with status {}",response.getHeader("X-Correlation-ID"),response.getStatus());
        //we can use MDC to get the correlation id from the thread
        logger.info("AfterCompletion: Request# ,{} finished with status {}", MDC.get("X-Correlation-ID"),response.getStatus());

        //clear tenantContext
        TenantContext.clear();
    }
}
