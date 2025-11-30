package org.example.alaa.mdcdemo.config;

import org.example.alaa.mdcdemo.interceptors.RequestInterceptor;

/**
 * WebConfig class to register new interceptor
 */

@org.springframework.context.annotation.Configuration
public class WebConfig implements org.springframework.web.servlet.config.annotation.WebMvcConfigurer {
    @Override
    public void addInterceptors(org.springframework.web.servlet.config.annotation.InterceptorRegistry registry) {
        registry.addInterceptor(new RequestInterceptor()); //adding RequestInterceptor to the registry
    }


}
