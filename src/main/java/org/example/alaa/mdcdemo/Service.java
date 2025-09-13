package org.example.alaa.mdcdemo;

import lombok.extern.slf4j.Slf4j;

@org.springframework.stereotype.Service
@Slf4j
public class Service {


    public void createUser(User user) {
        log.info("Get user information from controller : {}", user);
        log.info("TenantContext info : {}", TenantContext.getTenantInfo());
        //database operation
        log.info("User saved to database");
    }
}
