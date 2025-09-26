package org.example.alaa.mdcdemo.service;

import lombok.extern.slf4j.Slf4j;
import org.example.alaa.mdcdemo.context.TenantContext;
import org.example.alaa.mdcdemo.model.User;

@org.springframework.stereotype.Service
@Slf4j
public class Service {


    public void createUser(User user) {
        log.info("Get user information from controller : {}", user);
        log.info("TenantContext info : {}", TenantContext.getTenantInfo());
        //database operation
        log.info("User saved to database");
    }

    public void getUser() throws Exception {
        throw new Exception("User Not Found");
    }
}
